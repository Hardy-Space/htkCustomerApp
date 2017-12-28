package com.hl.htk_customer.hldc.bean;

/**
 * Created by Administrator on 2017/11/1.
 */

public class GvBean {
    private String imgurl;
    private String name;

    public GvBean(String imgurl, String name) {
        this.imgurl = imgurl;
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
