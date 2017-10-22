package com.example.maxcion_home.jdmall.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.interfaces.IBottomBarClickListener;

/**
 * Created by maxcion_home on 2017/9/7.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {

    private ImageView mHomeIv;
    private TextView mHomeTv;
    private TextView mCategoryTv;
    private ImageView mCategoryIv;
    private ImageView mShopcarIv;
    private TextView mShopcarTv;
    private ImageView mMyJDIv;
    private TextView mMyJDTv;
    private int mCurrenTabId;
    private IBottomBarClickListener listener;
    private LinearLayout homeLL;

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setIBottomBarClickListener(IBottomBarClickListener listener){
        this.listener = listener;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        homeLL = (LinearLayout) findViewById(R.id.frag_main_ll);
        homeLL.setOnClickListener(this);
        findViewById(R.id.frag_category_ll).setOnClickListener(this);
        findViewById(R.id.frag_shopcar_ll).setOnClickListener(this);
        findViewById(R.id.frag_mine_ll).setOnClickListener(this);

        mHomeIv = (ImageView) findViewById(R.id.frag_main_iv);
        mHomeTv = (TextView) findViewById(R.id.frag_main);
        mCategoryIv = (ImageView) findViewById(R.id.frag_category_iv);
        mCategoryTv = (TextView) findViewById(R.id.frag_category);
        mShopcarIv = (ImageView) findViewById(R.id.frag_shopcar_iv);
        mShopcarTv = (TextView) findViewById(R.id.frag_shopcar);
        mMyJDIv = (ImageView) findViewById(R.id.frag_mine_iv);
        mMyJDTv = (TextView) findViewById(R.id.frag_mine);

    }

    @Override
    public void onClick(View v) {
        if (mCurrenTabId ==v.getId()){
            return;
        }
        mHomeIv.setSelected(v.getId() == R.id.frag_main_ll);
        mHomeTv.setSelected(v.getId() == R.id.frag_main_ll);
        mCategoryIv.setSelected(v.getId() == R.id.frag_category_ll);
        mCategoryTv.setSelected(v.getId() == R.id.frag_category_ll);
        mShopcarIv.setSelected(v.getId() == R.id.frag_shopcar_ll);
        mShopcarTv.setSelected(v.getId() == R.id.frag_shopcar_ll);
        mMyJDIv.setSelected(v.getId() == R.id.frag_mine_ll);
        mMyJDTv.setSelected(v.getId() == R.id.frag_mine_ll);

        listener.onItemClick(v.getId());
        mCurrenTabId = v.getId();
    }
}
