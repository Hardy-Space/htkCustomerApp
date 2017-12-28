package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/10/21.
 */

public class WxPayFailedEntity {
    public static final String WAIMAI = "WAIMAI";
    public static final String TUANGOU = "TUANGOU";
    public static final String ZIZUDIANCAN = "ZIZUDIANCAN";

    private String extData;

    public WxPayFailedEntity(String extData) {
        this.extData = extData;
    }

    public String getExtData(){
        return extData;
    }
}
