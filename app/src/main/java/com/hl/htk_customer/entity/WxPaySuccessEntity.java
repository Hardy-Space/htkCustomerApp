package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/8/2.
 *
 */

public class WxPaySuccessEntity {

    public static final String WAIMAI = "WAIMAI";
    public static final String TUANGOU = "TUANGOU";
    public static final String ZIZUDIANCAN = "ZIZUDIANCAN";

    private String extData;

    public WxPaySuccessEntity(String extData) {
        this.extData = extData;
    }

    public String getExtData(){
        return extData;
    }


}
