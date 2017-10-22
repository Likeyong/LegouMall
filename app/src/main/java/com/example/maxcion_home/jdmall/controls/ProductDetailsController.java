package com.example.maxcion_home.jdmall.controls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.RcommentCount;
import com.example.maxcion_home.jdmall.bean.RcommentDetail;
import com.example.maxcion_home.jdmall.bean.RgoodComment;
import com.example.maxcion_home.jdmall.bean.RproductInfo;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.fragment.ProductCommentFragment;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maxcion_home on 2017/10/9.
 */

public class ProductDetailsController extends BaseController {
    public ProductDetailsController(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action) {
            case IdiyMessage.PRODUCT_INFO_ACTION:
                RproductInfo rproductInfo = handleProductIntroduce((int) values[0]);
                listener.onModeChanged(IdiyMessage.PRODUCT_INFO_ACTION_RESULT, rproductInfo);
                break;
            case IdiyMessage.GOOD_COMMENT_ACTION:
                List<RgoodComment> rgoodComments = handleGoodComment((int) values[0]);
                listener.onModeChanged(IdiyMessage.GOOD_COMMENT_ACTION_RESULT, rgoodComments);
                break;
            case IdiyMessage.GET_COMMENT_COUNT_ACTION:
                RcommentCount rcommentCount = handleGoodCommentCount((int) values[0]);
                listener.onModeChanged(IdiyMessage.GET_COMMENT_COUNT_ACTION_RESULT, rcommentCount);
                break;
            case IdiyMessage.GET_COMMENT_ACTION:
                List<RcommentDetail> rcommentDetails = handleGoodCommentType((int) values[0], (int) values[1]);
                listener.onModeChanged(IdiyMessage.GET_COMMENT_ACTION_RESULT, rcommentDetails);
                break;
            case IdiyMessage.ADD2SHOPCAR_ACTION:
                String add2CarResult = handleGoodAdd2Car((int) values[0],
                        (int) values[1], (int) values[2],(String)values[3]);
                listener.onModeChanged(IdiyMessage.ADD2SHOPCAR_ACTION_RESULT, add2CarResult);
                break;

        }
    }

    private String handleGoodAdd2Car(int userId, int productId, int buyCount, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("productId", productId + "");
        map.put("buyCount", buyCount + "");
        map.put("pversion", type);
        String jsonString = NetWorkUtil.doPost(NetWorkCons.TOSHOPCAR_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (!rResult.success){
            return rResult.errorMsg;
        }
        return "添加成功";
    }

    private List<RcommentDetail> handleGoodCommentType(int pid, int type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("productId", pid + "");
        if (type == 4) {// 希望有图=全部评论+hasImgCom
            map.put("type", ProductCommentFragment.ALL_COMMENT + "");
            // hasImgCom: required (boolean) 如果需要有图的评论才添加 如果没有则不用添加 比如选中好评
            map.put("hasImgCom", "true");
        } else {
            map.put("type", type + "");
        }
        String jsonStr = NetWorkUtil.doPost(NetWorkCons.COMMENTDETAIL_URL,
                map);
        RResult rResult = JSON.parseObject(jsonStr, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, RcommentDetail.class);
        }
        return null;
    }

    private RcommentCount handleGoodCommentCount(int value) {
        HashMap<String, String> map = new HashMap<>();
        map.put("productId", value + "");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.COMMENTCOUNT_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);

        if (rResult.success) {
            return JSON.parseObject(rResult.result, RcommentCount.class);
        }
        return null;
    }

    private List<RgoodComment> handleGoodComment(int value) {
        HashMap<String, String> map = new HashMap<>();
        map.put("productId", value + "");
        map.put("type", "1");
        String jsonString = NetWorkUtil.doPost(NetWorkCons.PRODUCTCOMMENT_URL, map);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, RgoodComment.class);
        }
        return null;
    }

    private RproductInfo handleProductIntroduce(int pid) {
        String jsonString = NetWorkUtil.doGet(NetWorkCons.PRODUCTINFO_URL + "?id=" + pid);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success == true) {
            return JSON.parseObject(rResult.result, RproductInfo.class);
        }
        return null;
    }
}
