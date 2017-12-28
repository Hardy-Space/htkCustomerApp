package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/11/4.
 */

public class ShopImageEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"shopImgUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shopImgUrl : http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg
         */

        private String shopImgUrl;

        public String getShopImgUrl() {
            return shopImgUrl;
        }

        public void setShopImgUrl(String shopImgUrl) {
            this.shopImgUrl = shopImgUrl;
        }
    }
}
