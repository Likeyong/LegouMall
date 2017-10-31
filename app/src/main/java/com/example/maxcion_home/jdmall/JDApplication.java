package com.example.maxcion_home.jdmall;

import android.app.Application;

import com.example.maxcion_home.jdmall.bean.RLoginResult;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by maxcion_home on 2017/9/9.
 */

public class JDApplication extends Application {
    public RLoginResult mRLoginResult ;

    @Override
    public void onCreate() {
        super.onCreate();

        ZXingLibrary.initDisplayOpinion(this);
    }
}
