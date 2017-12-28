package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class DianCanFenLeiEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"categoryName":"冷饮","shopId":1},{"id":2,"categoryName":"小吃","shopId":1}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * categoryName : 冷饮
         * shopId : 1
         */

        private int id;
        private String categoryName;
        private int shopId;

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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}
