package com.example.maxcion_home.jdmall.controls;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.bean.Banner;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.RgetLikeResult;
import com.example.maxcion_home.jdmall.bean.RgetLikeRows;
import com.example.maxcion_home.jdmall.bean.RsecKillRow;
import com.example.maxcion_home.jdmall.bean.Rseckill;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxcion_home on 2017/9/9.
 */

public class HomeController extends BaseController {
    public HomeController(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action) {
            case IdiyMessage.GET_BANNER_ACTION:
                List<Banner> banners = loadBanner((Integer) values[0]);
                listener.onModeChanged(IdiyMessage.GET_BANNER_ACTION_RESULT,
                        banners);
                break;
            case IdiyMessage.SECOND_KILL_ACTION:
                ArrayList<RsecKillRow> secondKillData = getSecondKillData();
                listener.onModeChanged(IdiyMessage.SECOND_KILL_ACTION_RESULT, secondKillData);
                break;
            case IdiyMessage.RECOMMEND_PRODUCT_ACTION:
                ArrayList<RgetLikeRows> youLike = getYouLike();//从服务器获取猜你喜欢模块数据
                listener.onModeChanged(IdiyMessage.RECOMMEND_PRODUCT_ACTION_RESULT, youLike);
        }
    }

    private ArrayList<RsecKillRow> getSecondKillData() {
        ArrayList<RsecKillRow> rSecKillRow = new ArrayList<>();
        String jsonString = NetWorkUtil.doGet(NetWorkCons.SECKILL_URL);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            Rseckill rSeckill = JSON.parseObject(rResult.result, Rseckill.class);
            rSecKillRow = (ArrayList<RsecKillRow>) JSON.parseArray(rSeckill.rows, RsecKillRow.class);
            return rSecKillRow;
        }
        return rSecKillRow;
    }

    private List<Banner> loadBanner(Integer value) {
        ArrayList<Banner> rBanner = new ArrayList<>();
        String jsonString = NetWorkUtil.doGet(NetWorkCons.BANNER_URL + "?adKind=" + value);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, Banner.class);
        }
        return rBanner;
    }

    public ArrayList<RgetLikeRows> getYouLike() {
        ArrayList<RgetLikeRows> youLike = new ArrayList<>();
        String jsonString = NetWorkUtil.doGet(NetWorkCons.GETYOURFAV_URL);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            RgetLikeResult rgetLikeResult = JSON.parseObject(rResult.result, RgetLikeResult.class);
            youLike = (ArrayList<RgetLikeRows>) JSON.parseArray(rgetLikeResult.rows, RgetLikeRows.class);
            return youLike;
        }
        return youLike;
    }
}
