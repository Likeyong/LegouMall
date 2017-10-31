package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.fragment.CategoryFragment;
import com.example.maxcion_home.jdmall.fragment.HomeFragment;
import com.example.maxcion_home.jdmall.fragment.MyJDFragment;
import com.example.maxcion_home.jdmall.fragment.ShopCarFragment;
import com.example.maxcion_home.jdmall.interfaces.IBottomBarClickListener;
import com.example.maxcion_home.jdmall.interfaces.OnScanAndVoiceListener;
import com.example.maxcion_home.jdmall.ui.BottomBar;
import com.example.maxcion_home.jdmall.ui.HeadView;
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
        reacherHead.setOnScanAndSpeakListener(this);
        man = getSupportFragmentManager();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setIBottomBarClickListener(this);
        bottomBar.findViewById(R.id.frag_main_ll).performClick();
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
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 1);
        Toast.makeText(this,"888",0).show();
    }

    @Override
    public void startSpeak() {

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
                        Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
