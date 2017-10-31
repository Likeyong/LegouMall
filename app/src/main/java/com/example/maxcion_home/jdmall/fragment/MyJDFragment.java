package com.example.maxcion_home.jdmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.HomeActivity;
import com.example.maxcion_home.jdmall.activity.LoginActivity;
import com.example.maxcion_home.jdmall.activity.OrderListActivity;
import com.example.maxcion_home.jdmall.bean.RLoginResult;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.UserController;
import com.example.maxcion_home.jdmall.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class MyJDFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.user_icon_iv)
    ImageView userIconIv;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_level_tv)
    TextView userLevelTv;
    @BindView(R.id.wait_pay_tv)
    TextView waitPayTv;
    @BindView(R.id.wait_pay_ll)
    LinearLayout waitPayLl;
    @BindView(R.id.wait_receive_tv)
    TextView waitReceiveTv;
    @BindView(R.id.wait_receive_ll)
    LinearLayout waitReceiveLl;
    @BindView(R.id.myjd_order)
    LinearLayout myjdOrder;
    @BindView(R.id.logout_btn)
    Button logoutBtn;
    private Unbinder unbinder;
    private RLoginResult mRLoginResult;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.CLEAR_USER_ACTION_RESULT:
                ActivityUtils.start2Activity(getContext(),
                        LoginActivity.class, true);
                break;
        }
    }

    @Override
    protected void initView() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.hideTitle(View.GONE);
        homeActivity.findViewById(R.id.logout_btn).setOnClickListener(this);
        JDApplication jdApplication = (JDApplication) getActivity().getApplication();
        mRLoginResult = jdApplication.mRLoginResult;
        userNameTv.setText(mRLoginResult.userName);
        waitPayTv.setText(mRLoginResult.waitPayCount + "");
        waitReceiveTv.setText(mRLoginResult.waitReceiveCount + "");
        String level = initUserLevel();
        userLevelTv.setText(level);
        myjdOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderListActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    private String initUserLevel() {
        String level = null;
        switch (mRLoginResult.userLevel) {
            case 1:
                level = "注册会员";
                break;
            case 2:
                level = "铜牌会员";
                break;
            case 3:
                level = "银牌会员";
                break;
            case 4:
                level = "金牌会员";
                break;
            case 5:
                level = "钻石会员";
                break;
        }
        return level;
    }

    @Override
    protected void initControl() {
        mControl = new UserController(getContext());
        mControl.setIModeChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();
        initView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myjd, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                mControl.sendAsyncMessage(IdiyMessage.CLEAR_USER_ACTION, 0);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
