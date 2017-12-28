package com.hl.htk_customer.hldc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/11/3.
 */

public class YiDianFoodParentBean {

    private String categoryName; //分类名字
    private String categoryId; //分类Id
    private List<OrderFoodBean> orderProductList = new ArrayList<>();

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<OrderFoodBean> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderFoodBean> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
