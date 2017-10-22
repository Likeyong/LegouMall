package com.example.maxcion_home.jdmall.ui.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by maxcion_home on 2017/9/24.
 */

public abstract class BasePopuWindow {
    protected PopupWindow mPopuWindow;
    protected Context mContext;


    protected abstract void initView();

    public abstract void onShow(View view);

    public void onDissmiss() {
        if (mPopuWindow != null) {
            mPopuWindow.dismiss();
        }
    }
}
