package com.example.maxcion_home.jdmall.controls;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.RorderList;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxcion_home on 2017/10/14.
 */

public class OrderController extends BaseController {
    public OrderController(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.COMPLETE_ORDER_ACTION:

                listener.onModeChanged(IdiyMessage.COMPLETE_ORDER_ACTION_RESULT,
                        handleCompleteOrder((int)values[0],(int)values[1]));
                break;

            case IdiyMessage.ALL_ORDER_ACTION:
                listener.onModeChanged(IdiyMessage.ALL_ORDER_ACTION_RESULT,
                        handleAllOrder((int)values[1]));
                break;
            case IdiyMessage.SEND_PRODUCT:
                listener.onModeChanged(IdiyMessage.SEND_PRODUCT_RESULT,
                        sendProduct((int) values[0],(int) values[1]));
                break;
            case IdiyMessage.CONFIRM_ORDER_ACTION:
                listener.onModeChanged(IdiyMessage.CONFIRM_ORDER_ACTION_RESULT,
                        sureReceiver((int) values[0], (int) values[1]));
                break;
            case IdiyMessage.CANCLE_ORDER:
                listener.onModeChanged(IdiyMessage.CANCLE_ORDER_RESULT,
                        sureCancleOrder((int) values[0], (int) values[1]));
                break;
        }

    }

    private String sureCancleOrder(int uid, int oid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",uid+"");
        map.put("oid",oid+"");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.CANCLEORDER_URL,map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success){
            return "恭喜您收到宝贝";
        }
        return rResult.errorMsg;
    }

    private String sureReceiver(int uid, int oid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",uid+"");
        map.put("oid",oid+"");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.CONFIRMORDER_URL,map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success){
            return "恭喜您收到宝贝";
        }
        return rResult.errorMsg;
    }

    private String sendProduct(int uid, int oid) {
        String jsonString = NetWorkUtil.doGet(
                "http://mall.520it.com/deliver?userId=" + uid + "&oid=" + oid);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success){
            return "已提醒卖家发货";
        }
        return rResult.errorMsg;
    }


    private List<RorderList> handleAllOrder(int uid) {
        List<RorderList> waitPayList = handleCompleteOrder(0, uid);
        List<RorderList> waitSendList = handleCompleteOrder(1, uid);
        List<RorderList> waitReceiverList = handleCompleteOrder(2, uid);
        List<RorderList> completList = handleCompleteOrder(3, uid);
        List<RorderList> allList = new ArrayList<>();
        allList.addAll(waitPayList);
        allList.addAll(waitSendList);
        allList.addAll(waitReceiverList);
        allList.addAll(completList);
        return allList;


    }

    private List<RorderList> handleCompleteOrder(int orderState, int uid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("status",orderState+"");
        map.put("userId",uid+"");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.GETORDERBYSTATUS_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success){
            return JSON.parseArray(rResult.result, RorderList.class);
        }
        return null;
    }
}
