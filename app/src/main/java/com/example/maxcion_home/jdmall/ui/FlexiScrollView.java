package com.example.maxcion_home.jdmall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;


/**
 * Created by maxcion_home on 2017/9/9.
 */

public class FlexiScrollView extends ScrollView{
    //初始可拉动Y轴方向距离
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;
    //上下文环境
    private Context mContext;
    //实际可上下拉动Y轴上的距离
    private int mMaxYOverscrollDistance;

    public FlexiScrollView(Context context){
        super(context);
        mContext = context;
        initBounceListView();
    }

    public FlexiScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBounceListView();
    }

    public FlexiScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView(){
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //实现的本质就是在这里动态改变了maxOverScrollY的值
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

}
