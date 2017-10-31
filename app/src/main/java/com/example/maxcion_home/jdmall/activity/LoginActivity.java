package com.example.maxcion_home.jdmall.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RLoginResult;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.UserController;
import com.example.maxcion_home.jdmall.util.AESUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    TextInputLayout mUserName;
    TextInputLayout mPassWorld;
    UserController mUserControl;

    @BindView(R.id.bt_login)
    Button loginBton;

    @BindView(R.id.tv_registe_login)
    TextView tvRegiste;


    protected void handleLoginResult(Message msg) {
        RResult rresult = (RResult) msg.obj;
        RLoginResult loginResult = JSON.parseObject(rresult.result, RLoginResult.class);
        if (rresult.success) {
            ((JDApplication)getApplication()).mRLoginResult=loginResult;
            String userName = mUserName.getEditText().getText().toString();
            String passWorld = mPassWorld.getEditText().getText().toString();
            try {
                userName = AESUtils.encrypt(userName);
                passWorld = AESUtils.encrypt(passWorld);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mUserControl.sendAsyncMessage(IdiyMessage.SAVE_USERTODB, userName, passWorld);

        } else {
            tip("登陆失败:" + rresult.errorMsg);
        }
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.LOGIN_ACTION_RESULT:
                handleLoginResult(msg);
                break;

            case IdiyMessage.SAVE_USERTODB_RESULT:
                handleSave2DBResult((boolean) msg.obj);
                break;

            case IdiyMessage.GET_USER_ACTION_RESULT:
                handleGetUser((HashMap<String, String>) msg.obj);
                break;
        }
    }

    private void handleGetUser(HashMap<String, String> obj) {
        if (obj != null) {
            String userName = obj.get("name");
            String pwd = obj.get("pwd");
            mUserName.getEditText().setText(userName);
            mPassWorld.getEditText().setText(pwd);
        }
    }

    private void handleSave2DBResult(boolean isSuccess) {
        if (isSuccess) {
            start2Activity(this, HomeActivity.class, true);
        } else {
            tip("数据异常");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mUserControl = new UserController(this);
        mUserControl.setIModeChangeListener(this);
        initView();
        setOnclickMethod();


    }

    @OnClick(R.id.tv_registe_login)
    void regist() {
        start2Activity(this, RegistActivity.class, false);
    }

    private void setOnclickMethod() {

        loginBton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getEditText().getText().toString();
                String passWorld = mPassWorld.getEditText().getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWorld)) {
                    tip("账号或密码不能为空");
                    return;
                }
                mUserControl.sendAsyncMessage(IdiyMessage.LOGIN_ACTION, userName, passWorld);

            }
        });
    }

    private void initView() {
        mUserName = (TextInputLayout) findViewById(R.id.txip_username);
        mPassWorld = (TextInputLayout) findViewById(R.id.txip_passworld);
        mUserName.setHint("手机号/邮箱/用户名");
        mPassWorld.setHint("请输入密码");
        mUserControl.sendAsyncMessage(IdiyMessage.GET_USER_ACTION, 0);

    }

}
