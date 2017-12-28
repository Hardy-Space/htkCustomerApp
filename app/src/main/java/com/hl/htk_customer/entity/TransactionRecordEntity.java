package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class TransactionRecordEntity {

    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"orderNumber":"123456789","orderType":null,"payMethod":null,"payAmount":10,"shopId":1,"accountToken":"db5af7ca-e946-483a-8acd-d3b0678a4c8f"}]
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
         * orderNumber : 123456789
         * orderType : null
         * payMethod : null
         * payAmount : 10.0
         * shopId : 1
         * accountToken : db5af7ca-e946-483a-8acd-d3b0678a4c8f
         * createTime":"2017-11-06 00:00:00
         */

        private int id;
        private String orderNumber;
        private int orderType;
        private int payMethod;
        private double payAmount;
        private int shopId;
        private String accountToken;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(int payMethod) {
            this.payMethod = payMethod;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getAccountToken() {
            return accountToken;
        }

        public void setAccountToken(String accountToken) {
            this.accountToken = accountToken;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
