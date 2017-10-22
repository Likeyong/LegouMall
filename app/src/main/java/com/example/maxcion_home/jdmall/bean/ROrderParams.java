package com.example.maxcion_home.jdmall.bean;

/**
 * Created by maxcion_home on 2017/10/13.
 */

public class ROrderParams {
    public int errorType;//   ": (0-成功 1-产品没库存了 2-系统失败)
    public String buyTime;//   ": "2016-03-16 14:37:35",
    public double freight;//   ": 运费,
    public double totalPrice;//       ":应付总金额,                      ],
    public int oid;//   ": 订单id,
    public long orderNum;//   ": "订单号",
    public String pname;//   ": "京东商品",
    public int payWay;//   ": 付款方式（ 0在线支付 1货到付款）,
    public String tn;//   ": "订单令牌",
    public double allPrice;//   ": 商品总价格
}
