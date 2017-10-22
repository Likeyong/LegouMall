package com.example.maxcion_home.jdmall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.adapter.ChooseAddressAdapter;
import com.example.maxcion_home.jdmall.bean.RreceivrAddress;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ShopcarController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseReceiverActivity extends BaseActivity implements CategoryTopAdapter.OnItemClickListener{

    @BindView(R.id.lv)
    RecyclerView recyclerView;
    private ChooseAddressAdapter mAdapter;
    private List<RreceivrAddress> mAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activity);
        ButterKnife.bind(this);
        initView();
        initController();
        JDApplication app = (JDApplication) getApplication();
        mController.sendAsyncMessage(IdiyMessage.CHOOSE_RECEIVER_ACTION,app.mRLoginResult.id);
    }

    private void initView() {
        mAdapter = new ChooseAddressAdapter();
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initController() {
        mController = new ShopcarController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.CHOOSE_RECEIVER_ACTION_RESULT:
                handleAddressList(msg.obj);
                break;
        }
    }

    private void handleAddressList(Object obj) {
        if (obj==null){
            tip("数据异常");
            return;
        }
        mAddresses = (List<RreceivrAddress>) obj;
        mAdapter.setmData(mAddresses,this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, final int postion) {
        switch (view.getId()){
            case R.id.delete_tv:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("请您确认是否删除该地址")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAddresses.remove(postion);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("算了吧", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.has_receiver_rl:
                Intent intent = new Intent();
                intent.putExtra("CHOOSE_ADDRESS",mAddresses.get(postion));
                setResult(RESULT_OK,intent);
                finish();
                break;
        }

    }
}
