package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/6/29.
 */

public class SuccessEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"orderTime":"2017-06-29 16:35:16","orderId":5}
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
         * orderTime : 2017-06-29 16:35:16
         * orderId : 5
         */

        private String orderTime;
        private int orderId;

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
