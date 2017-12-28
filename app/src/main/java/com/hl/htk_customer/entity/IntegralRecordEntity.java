package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class IntegralRecordEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"title":"积分消费-800","integralValue":800,"recordType":0,"shopId":1,"accountToken":"db5af7ca-e946-483a-8acd-d3b0678a4c8f"}]
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
         * title : 积分消费-800
         * integralValue : 800
         * recordType : 0
         * shopId : 1
         * accountToken : db5af7ca-e946-483a-8acd-d3b0678a4c8f
         * recordTime
         */

        private int id;
        private String title;
        private int integralValue;
        private int recordType;
        private int shopId;
        private String accountToken;
        private String recordTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIntegralValue() {
            return integralValue;
        }

        public void setIntegralValue(int integralValue) {
            this.integralValue = integralValue;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
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

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }
    }
}
