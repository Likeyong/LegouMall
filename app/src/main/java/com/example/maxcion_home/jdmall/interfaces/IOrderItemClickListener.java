package com.example.maxcion_home.jdmall.interfaces;

/**
 * Created by maxcion_home on 2017/10/14.
 */

public interface IOrderItemClickListener {
    void toPayOrder(int position);
    void tosendOrder(int position);
    void toCancleOrder(int position);
    void sureGetOrder(int position);
}
