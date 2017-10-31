package com.example.maxcion_home.jdmall.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.interfaces.OnScanAndVoiceListener;

/**
 * Created by maxcion_home on 2017/10/30.
 */

public class HeadView extends LinearLayout implements View.OnClickListener {

    public SearchView searchView;

    private OnScanAndVoiceListener listener;
    public ImageView scanImg;

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.head_layout, this);
        searchView = (SearchView) findViewById(R.id.search);
        scanImg = (ImageView) findViewById(R.id.scan_iv);
        ImageView message = (ImageView) findViewById(R.id.message_iv);
        scanImg.setOnClickListener(this);
        message.setOnClickListener(this);
    }

    public void setOnQueryTextListener(SearchView.OnQueryTextListener listener) {
        searchView.setOnQueryTextListener(listener);
    }

    public void setOnScanAndSpeakListener(OnScanAndVoiceListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_iv:
                listener.startScanner();
                break;
            case R.id.message_iv:
                listener.startSpeak();
                break;
        }
    }
}
