package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */

public class TgOrderListEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"orderId":13,"shopName":"鲜芋仙2","logoUrl":"http://192.168.100.6:8080/htkApp/upload/admin/shopCategory/meishi.png","orderTime":"2017-06-30 15:07:13","orderState":1,"orderAmount":0.01,"packageName":"我家牛排","packageId":1}]
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
         * orderId : 13
         * shopName : 鲜芋仙2
         * logoUrl : http://192.168.100.6:8080/htkApp/upload/admin/shopCategory/meishi.png
         * orderTime : 2017-06-30 15:07:13
         * orderState : 1
         * orderAmount : 0.01
         * packageName : 我家牛排
         * packageId : 1
         */

        private int orderId;
        private String shopName;
        private String logoUrl;
        private String orderTime;
        private int orderState;
        private double orderAmount;
        private String packageName;
        private int packageId;
        private int shopId;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
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

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getPackageId() {
            return packageId;
        }

        public void setPackageId(int packageId) {
            this.packageId = packageId;
        }
    }
}
