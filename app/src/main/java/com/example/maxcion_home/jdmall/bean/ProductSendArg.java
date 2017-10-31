package com.example.maxcion_home.jdmall.bean;

/**
 * Created by maxcion_home on 2017/9/24.
 */

public class ProductSendArg {
    public int categoryId;
    public int filterType=1;//1-综合 2-新品 3-评价
    public int sortType;//1-销量 2-价格高到低 3-价格低到高
    public int deliverChoose;//0-代表无选择 1代表京东配送 2-代表货到付款 4-代表仅看有货 3代表条件1+2 5代表条件1+4 6代表条件2+4
    public int minPrice;//最低价格
    public int maxPrice;//最高价格
    public int brandId;//最高价格
    public String keyword;
}
