package com.example.maxcion_home.jdmall.bean;

import java.io.Serializable;

/**
 * Created by maxcion_home on 2017/10/12.
 */

public class RreceivrAddress implements Serializable{
    public int id;//      ": 地址id,
    public boolean isDefault;//      ": false,
    public String receiverName;//      ": "接收人",
    public String receiverAddress;//      ": "具体地址",
    public String receiverPhone;//      ": "手机号"
}
