package com.example.maxcion_home.jdmall.bean;

import java.io.Serializable;

/**
 * Created by maxcion_home on 2017/10/12.
 */

public class Rshopcar implements Serializable{
           public int id        ;//": 购物车明细id,
           public int buyCount  ;//    ": 购买数,
           public String storeName ;//    ": "商店名称",
           public double pprice    ;//    ": 价格,
           public String pimageUrl ;//    ": "商品图片路径",
           public String pname     ;//    ": "商品名称",
           public int pid       ;//    ": 商品id,
           public int stockCount;//        ": 库存,
           public int storeId   ;//    ": 商店id,
           public String pversion  ;//        ": "商品版本"
}
