package com.example.maxcion_home.jdmall.interfaces;

/**
 * Created by maxcion_home on 2017/9/24.
 */

public interface IProductSortChanegListener {
    int ALLSORT=0x001;
    int NEWSSORT=0x002;
    int COMMENTSORT=0x003;

    void onSortChanged(int action);
}
