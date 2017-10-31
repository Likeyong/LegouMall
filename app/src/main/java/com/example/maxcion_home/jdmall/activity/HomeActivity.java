package com.example.maxcion_home.jdmall.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.fragment.CategoryFragment;
import com.example.maxcion_home.jdmall.fragment.HomeFragment;
import com.example.maxcion_home.jdmall.fragment.MyJDFragment;
import com.example.maxcion_home.jdmall.fragment.ShopCarFragment;
import com.example.maxcion_home.jdmall.interfaces.IBottomBarClickListener;
import com.example.maxcion_home.jdmall.interfaces.OnScanAndVoiceListener;
import com.example.maxcion_home.jdmall.ui.BottomBar;
import com.example.maxcion_home.jdmall.ui.HeadView;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends FragmentActivity implements IBottomBarClickListener, OnScanAndVoiceListener {


    @BindView(R.id.reacher_head)
    public HeadView reacherHead;
    private FragmentManager man;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ZXingLibrary.initDisplayOpinion(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59eb68a8");
        int id = reacherHead.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        reacherHead.setOnScanAndSpeakListener(this);
        man = getSupportFragmentManager();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setIBottomBarClickListener(this);
        bottomBar.findViewById(R.id.frag_main_ll).performClick();
        reacherHead.scanImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 5);
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int action) {

        FragmentTransaction transaction = man.beginTransaction();
        switch (action) {
            case R.id.frag_main_ll:
                transaction.replace(R.id.top_bar, new HomeFragment());
                break;
            case R.id.frag_category_ll:
                transaction.replace(R.id.top_bar, new CategoryFragment());
                break;
            case R.id.frag_shopcar_ll:
                transaction.replace(R.id.top_bar, new ShopCarFragment());
                break;
            case R.id.frag_mine_ll:
                transaction.replace(R.id.top_bar, new MyJDFragment());
                break;

        }
        transaction.commit();
    }

    public void hideTitle(int isVisiable) {
        reacherHead.setVisibility(isVisiable);
    }

    @Override
    public void startScanner() {

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示")
                    .setMessage("为了您更好的使用扫描功能，请您赋予相机权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                        }
                    })
                    .show();
        } else {
            toScanner();
        }


    }

    private void toScanner() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void startSpeak() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示")
                    .setMessage("为了您更好的使用语音输入，请您赋予录音权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
                        }
                    })
                    .show();
        } else {
            discernVoice();
        }


    }

    private void discernVoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog recognizerDialog = new RecognizerDialog(this, null);
        //2.设置accent、language等参数
        recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//语种，这里可以有zh_cn和en_us
        recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置口音，这里设置的是汉语普通话 具体支持口音请查看讯飞文档，
        recognizerDialog.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");//设置编码类型

        //其他设置请参考文档http://www.xfyun.cn/doccenter/awd
        //3.设置讯飞识别语音后的回调监听
        recognizerDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {//返回结果
                if (!b) {
                    String jsonString = recognizerResult.getResultString();
                    JSONObject jsonObject = JSON.parseObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("ws");
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("cw");
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                        String w = jsonObject2.getString("w");
                        stringBuffer.append(w);
                    }
                    reacherHead.searchView.setQuery(stringBuffer.toString(), false);
                    reacherHead.searchView.setIconified(false);
                }
            }

            @Override
            public void onError(SpeechError speechError) {//返回错误
                Log.e("返回的错误码", speechError.getErrorCode() + "");
            }

        });
        //显示讯飞语音识别视图
        recognizerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        toProductActivity(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 5:
                if (data != null) {
                    Uri uri = data.getData();
                    ContentResolver cr = getContentResolver();
                    try {
                        Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
                        String[] proj={MediaStore.Images.Media.DATA};
                        Cursor cursor=managedQuery(uri,proj,null,null,null);
                        int column_index =cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path=cursor.getString(column_index);
                        CodeUtils.analyzeBitmap(path, new CodeUtils.AnalyzeCallback() {
                            @Override
                            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                toProductActivity(result);
                            }

                            @Override
                            public void onAnalyzeFailed() {
                                Toast.makeText(HomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                            }
                        });

                        if (mBitmap != null) {
                            mBitmap.recycle();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }


    }

    private void toProductActivity(String result) {
        Intent toProductDetial = new Intent(HomeActivity.this,ProductDetialsActivity.class);
        toProductDetial.putExtra("productId",Integer.valueOf(result));
        startActivity(toProductDetial);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    discernVoice();
                }
                break;
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toScanner();
                }
                break;
        }
    }
}
