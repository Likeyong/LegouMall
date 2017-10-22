package com.example.maxcion_home.jdmall.controls;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.Raddress;
import com.example.maxcion_home.jdmall.bean.RaddressBean;
import com.example.maxcion_home.jdmall.bean.Rarea;
import com.example.maxcion_home.jdmall.bean.RreceivrAddress;
import com.example.maxcion_home.jdmall.bean.Rshopcar;
import com.example.maxcion_home.jdmall.bean.SAddOrderParams;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maxcion_home on 2017/10/12.
 */

public class ShopcarController extends BaseController {
    public ShopcarController(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action) {
            case IdiyMessage.SHOPCAR_LIST_ACTION:
                List<Rshopcar> rshopcars = handleShopcarList((int) values[0]);
                listener.onModeChanged(IdiyMessage.SHOPCAR_LIST_ACTION_RESULT, rshopcars);
                break;
            case IdiyMessage.DELET_SHOPCAR_ACTION:
                String result = handleDelectProduct((int) values[0], (int) values[1]);
                listener.onModeChanged(IdiyMessage.DELET_SHOPCAR_ACTION_RESULT, result);
                break;
            case IdiyMessage.GET_DEFAULT_RECEIVER_ACTION:
                RreceivrAddress rreceivrAddress = handleDefaultAddress((int) values[0]);
                listener.onModeChanged(IdiyMessage.GET_DEFAULT_RECEIVER_ACTION_RESULT, rreceivrAddress);
                break;
            case IdiyMessage.PROVINCE_ACTION:
                List<Rarea> rareas = handleArea(NetWorkCons.PROVINCE_URL, "");
                listener.onModeChanged(IdiyMessage.PROVINCE_ACTION_RESULT, rareas);
                break;
            case IdiyMessage.CITY_ACTION:
                List<Rarea> city = handleArea(NetWorkCons.CITY_URL, (String) values[0]);
                listener.onModeChanged(IdiyMessage.CITY_ACTION_RESULT, city);
                break;
            case IdiyMessage.AREA_ACTION:
                List<Rarea> area = handleArea(NetWorkCons.AREA_URL, (String) values[0]);
                listener.onModeChanged(IdiyMessage.AREA_ACTION_RESULT, area);
                break;
            case IdiyMessage.ADD_RECEIVER_ACTION:
                RaddressBean raddressBean = handleAddReceive((Raddress) values[0]);
                listener.onModeChanged(IdiyMessage.ADD_RECEIVER_ACTION_RESULT, raddressBean);
                break;
            case IdiyMessage.CHOOSE_RECEIVER_ACTION:
                
                listener.onModeChanged(IdiyMessage.CHOOSE_RECEIVER_ACTION_RESULT, loadReceiver((int) values[0]));
                break;
            case IdiyMessage.ADD_ORDER_ACTION:
                
                listener.onModeChanged(IdiyMessage.ADD_ORDER_ACTION_RESULT, addOrder((SAddOrderParams) values[0]));
                break;

        }
    }

    private RResult addOrder(SAddOrderParams sAddOrderParams) {
        String detail = JSON.toJSONString(sAddOrderParams);
        HashMap<String,String> map = new HashMap<>();
        map.put("detail",detail);
        String jsonString = NetWorkUtil.doPost(NetWorkCons.ADDORDER_URL, map);
        return JSON.parseObject(jsonString,RResult.class);
    }

    private List<RreceivrAddress> loadReceiver(int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", id + "");
        map.put("isDefault", "false");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.RECEIVEADDRESS_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(
                    rResult.result, RreceivrAddress.class);

        }
        return null;
    }

    private RaddressBean handleAddReceive(Raddress address) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", address.userId + "");
        map.put("addressDetails", address.addressDetails);
        map.put("cityCode", address.cityCode);
        map.put("distCode", address.distCode);
        map.put("name", address.name);
        map.put("phone", address.phone);
        map.put("provinceCode", address.provinceCode);
        map.put("isDefault", address.isDefault + "");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.ADDADDRESS_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseObject(rResult.result, RaddressBean.class);
        }
        return null;

    }

    private List<Rarea> handleArea(String url, String code) {
        String jsonString;
        if ("".equals(code)) {
            jsonString = NetWorkUtil.doGet(url);
        }
        jsonString = NetWorkUtil.doGet(url + "?fcode=" + code);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, Rarea.class);
        }
        return null;
    }

    private RreceivrAddress handleDefaultAddress(int userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("isDefault", "true");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.RECEIVEADDRESS_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            List<RreceivrAddress> addressList = JSON.parseArray(
                    rResult.result, RreceivrAddress.class);
            if (addressList.size()>0){
                return addressList.get(0);
            }
        }
        return null;
    }

    private String handleDelectProduct(int userId, int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("id", id + "");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.DELSHOPCAR_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return "删除成功";
        }
        return rResult.errorMsg;
    }

    private List<Rshopcar> handleShopcarList(int userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.SHOPCAR_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, Rshopcar.class);
        }
        return null;
    }
}
