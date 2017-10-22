package com.example.maxcion_home.jdmall.bean;

import java.io.Serializable;

/**
 * Created by maxcion_home on 2017/10/13.
 */

public class Raddress implements Serializable{
    public int userId;//: required (integer)
    public String name;//: required (string)
    public String phone;//: required (string)
    public String provinceCode;//: required (string)
    public String cityCode;//: required (string)
    public String distCode;//: required (string)
    public boolean isDefault;//    : required (boolean)
    public String addressDetails;// required (string)
}
