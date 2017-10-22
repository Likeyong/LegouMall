package com.example.maxcion_home.jdmall.bean;

import java.util.List;

/**
 * Created by maxcion_home on 2017/10/13.
 */

public class SAddOrderParams {


    public List<SaddOrderProductParams> products;
    public int addrId;//": 地址id
    public int payWay;//": (0在线支付 1货到付款)
    public int userId;//": 用户id
}
