package com.hl.htk_customer.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ProductEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"categoryName":"美食","description":"美食类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png"},{"id":2,"categoryName":"甜品饮品","description":"甜品饮品类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/tianpin.png"},{"id":3,"categoryName":"商家超市","description":"商家超市类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/chaoshi.png"},{"id":4,"categoryName":"果蔬生鲜","description":"果蔬生鲜类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/guoshu.png"},{"id":5,"categoryName":"新店特惠","description":"新店特惠类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/xindian.png"},{"id":6,"categoryName":"准时达","description":"准时达类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/zhunshi.png"},{"id":7,"categoryName":"下午茶","description":"下午茶类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/xiawu.png"},{"id":8,"categoryName":"土豪推荐","description":"土豪推荐类别","categoryUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/tuhao.png"}]
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * categoryName : 美食
         * description : 美食类别
         * categoryUrl : http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png
         */

        private int id;
        private String categoryName;
        private String description;
        private String categoryUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategoryUrl() {
            return categoryUrl;
        }

        public void setCategoryUrl(String categoryUrl) {
            this.categoryUrl = categoryUrl;
        }
    }
}
