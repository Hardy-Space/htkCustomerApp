package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class DianCanOrderEntity  {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":3,"orderTime":"2017-08-01 15:44:18.0","seatName":"Ａ区1座","shopName":"冰雪怪","logoUrl":"htkApp/upload/admin/shopCategory/meishi.png","shopId":1,"payState":1},{"id":5,"orderTime":"2017-08-19 14:33:49.0","seatName":"Ａ区1座","shopName":"冰雪怪","logoUrl":"htkApp/upload/admin/shopCategory/meishi.png","shopId":1,"payState":0}]
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
         * id : 3
         * orderTime : 2017-08-01 15:44:18.0
         * seatName : Ａ区1座
         * shopName : 冰雪怪
         * logoUrl : htkApp/upload/admin/shopCategory/meishi.png
         * shopId : 1
         * payState : 1
         */

        private int id;
        private String orderTime;
        private String seatName;
        private String shopName;
        private String logoUrl;
        private int shopId;
        private int payState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }
    }
}
