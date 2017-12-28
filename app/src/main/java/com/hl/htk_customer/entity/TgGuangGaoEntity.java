package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class TgGuangGaoEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":5,"imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/tianpin.jpg","adName":"甜筒","shopId":5},{"id":1,"imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/niupai.jpg","adName":"冰激凌","shopId":1},{"id":4,"imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/taocan.jpg","adName":"甜筒","shopId":4},{"id":3,"imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/tankao.jpg","adName":"甜筒","shopId":3},{"id":2,"imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/shuijiao.jpg","adName":"甜筒","shopId":2}]
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
         * id : 5
         * imgUrl : http://192.168.100.6:8080/htkApp/upload/shop/advertising/tianpin.jpg
         * adName : 甜筒
         * shopId : 5
         */

        private int id;
        private String imgUrl;
        private String adName;
        private int shopId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}
