package com.example.maxcion_home.jdmall.controls;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.db.UserDao;
import com.example.maxcion_home.jdmall.util.AESUtils;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import java.util.HashMap;

/**
 * Created by maxcion_home on 2017/9/5.
 */

public class UserController extends BaseController {


    private UserDao mUserDao;

    public UserController(Context mContext) {
        super(mContext);
        mUserDao = new UserDao(mContext);
    }

    /*
        子线程内执行的方法
         */
    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action) {
            case IdiyMessage.LOGIN_ACTION:
                RResult login = login(NetWorkCons.LOGIN_URL, (String) values[0], (String) values[1]);
                listener.onModeChanged(IdiyMessage.LOGIN_ACTION_RESULT, login);
                break;

            case IdiyMessage.REGIST_ACTION:
                RResult regist = regist(NetWorkCons.REGIST_URL, (String) values[0], (String) values[1]);
                listener.onModeChanged(IdiyMessage.REGIST_ACTION_RESULT, regist);
                break;

            case IdiyMessage.SAVE_USERTODB:
                boolean isSuccess = saveUserToDB((String) values[0], (String) values[1]);
                listener.onModeChanged(IdiyMessage.SAVE_USERTODB_RESULT, isSuccess);
                break;

            case IdiyMessage.GET_USER_ACTION:
                HashMap<String, String> lastUser = getUserFromDB();
                listener.onModeChanged(IdiyMessage.GET_USER_ACTION_RESULT,lastUser);
                break;
            case IdiyMessage.CLEAR_USER_ACTION:
                clearLastUser();
                listener.onModeChanged(IdiyMessage.CLEAR_USER_ACTION_RESULT,0);
                break;
        }
    }

    private void clearLastUser() {
        mUserDao.deleteUser();
    }


    public HashMap<String, String> getUserFromDB() {
        HashMap<String, String> lastUser = mUserDao.getLastUser();
        HashMap<String, String> map = null;
        try {
            String name = AESUtils.decrypt(lastUser.get("name"));
            String pwd = AESUtils.decrypt(lastUser.get("pwd"));
            map = new HashMap<>();
            map.put("name", name);
            map.put("pwd", pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private boolean saveUserToDB(String username, String pwd) {

        mUserDao.deleteUser();
        return mUserDao.saveUser(username, pwd);
    }

    private RResult regist(String registUrl, String username, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("pwd", pwd);
        String loginJson = NetWorkUtil.doPost(registUrl, params);
        return JSON.parseObject(loginJson, RResult.class);
    }

    /*
    登陆逻辑，并处理Json解析，因为Json解析属于耗时操作，放在子线程处理，此时login处于子线程中
     */
    private RResult login(String loginUrl, String username, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("pwd", pwd);
        String loginJson = NetWorkUtil.doPost(loginUrl, params);
        return JSON.parseObject(loginJson, RResult.class);
    }


}
