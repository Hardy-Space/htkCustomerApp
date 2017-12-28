package com.hl.htk_customer.hldc.bean;

/**
 * Created by asus on 2017/11/2.
 */

public class CategoryBean {
    private String id; //分类id
    private String categoryName; //分类名称
    private int shopId; //店铺id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
