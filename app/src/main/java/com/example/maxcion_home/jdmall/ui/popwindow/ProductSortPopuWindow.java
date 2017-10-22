package com.example.maxcion_home.jdmall.ui.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.interfaces.IProductSortChanegListener;

import butterknife.BindView;

/**
 * Created by maxcion_home on 2017/9/24.
 */

public class ProductSortPopuWindow extends BasePopuWindow implements View.OnClickListener {

    @BindView(R.id.all_sort)
    TextView allSort;
    @BindView(R.id.new_sort)
    TextView newSort;
    @BindView(R.id.comment_sort)
    TextView commentSort;

    private IProductSortChanegListener listener;

    public ProductSortPopuWindow(Context context) {
        mContext = context;
        initView();
    }

    public void setProductSortListener(IProductSortChanegListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_sort_pop_view, null, false);
        mPopuWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view.findViewById(R.id.all_sort).setOnClickListener(this);
        view.findViewById(R.id.new_sort).setOnClickListener(this);
        view.findViewById(R.id.comment_sort).setOnClickListener(this);


        mPopuWindow.setFocusable(true);
        mPopuWindow.setOutsideTouchable(true);
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopuWindow.update();
    }

    @Override
    public void onShow(View view) {
        if (mPopuWindow != null) {
            mPopuWindow.showAsDropDown(view, 0, 0);
        }
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            switch (view.getId()) {
                case R.id.all_sort:
                    listener.onSortChanged(IProductSortChanegListener.ALLSORT);
                    break;
                case R.id.new_sort:
                    listener.onSortChanged(IProductSortChanegListener.NEWSSORT);
                    break;
                case R.id.comment_sort:
                    listener.onSortChanged(IProductSortChanegListener.COMMENTSORT);
                    break;

            }
        }
        onDissmiss();
    }
}
