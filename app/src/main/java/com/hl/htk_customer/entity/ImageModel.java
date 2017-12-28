package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ImageModel {

    private String url;
    private int shopId;


    public ImageModel(String url, int shopId) {
        this.url = url;
        this.shopId = shopId;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
