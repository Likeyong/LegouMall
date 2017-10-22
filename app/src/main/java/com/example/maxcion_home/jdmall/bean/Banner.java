package com.example.maxcion_home.jdmall.bean;

/**
 * Created by maxcion_home on 2017/9/9.
 */

public class Banner {
    public long id;

    public int type;  //跳转类型（1跳转到网页，2跳转到商品详情，3跳转到分类去）

    public String adUrl; //图片路径

    public String webUrl;//如果是跳转网页类型，则返回网页地址

    public int adKind;//广告类型（1为导航banner，2为广告banner）

}