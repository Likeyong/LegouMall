package com.example.maxcion_home.jdmall.bean;

/**
 * Created by maxcion_home on 2017/9/12.
 */

public class RsecKillRow {
    public double allPrice;//"原价"
    public double pointPrice;// "秒杀价格",
    public String iconUrl;// "商品图片路径",
    public int timeLeft; //秒杀剩余时间（分钟）
    public int type;//   秒杀类型（1抢年货2超值3热卖 ）
    public int productId;// 商品id


//    "total": "秒杀商品总数",
//            "rows": [
//    {
//        "allPrice": "原价",
//            "pointPrice": "秒杀价格",
//            "iconUrl": "商品图片路径",
//            "timeLeft": 秒杀剩余时间（分钟）,
//        "type": 秒杀类型（1抢年货，2超值，3热卖）,
//        "productId": 商品id
//    }
//      ]
}
