package com.example.maxcion_home.jdmall.controls;

import android.content.Context;

import com.example.maxcion_home.jdmall.interfaces.IModeChangeListener;

/**
 * Created by maxcion_home on 2017/9/5.
 */

public abstract class BaseController {
    protected IModeChangeListener listener;
    protected Context mContext ;

    public BaseController(Context mContext) {
        this.mContext = mContext;
    }

    public void setIModeChangeListener(IModeChangeListener linstener) {
        this.listener = linstener;
    }

    public void sendAsyncMessage(final int action, final Object... values) {
        new Thread() {
            public void run() {
                handleMessage(action, values);
            }
        }.start();
    }


    protected abstract void handleMessage(int action, Object... values);
}
