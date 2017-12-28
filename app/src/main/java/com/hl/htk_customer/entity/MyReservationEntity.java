package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MyReservationEntity {

    /**
     * code : 100
     * message : 成功
     * data : [{"shopId":1,"shopName":null,"state":0,"logoUrl":null,"id":3,"orderNumber":"1711045265610070","scheduledName":"殷先生","seatCount":2,"scheduledTime":"2017-10-25 16:33","seatPhone":"18660706071","accountToken":"db5af7ca-e946-483a-8acd-d3b0678a4c8f","remarks":null,"status":0,"gmtCreate":null,"gmtModified":null,"orderTime":"2017-11-04 14:37:10.0","mark":0}]
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
         * shopId : 1
         * shopName : null
         * state : 0
         * logoUrl : null
         * id : 3
         * orderNumber : 1711045265610070
         * scheduledName : 殷先生
         * seatCount : 2
         * scheduledTime : 2017-10-25 16:33
         * seatPhone : 18660706071
         * accountToken : db5af7ca-e946-483a-8acd-d3b0678a4c8f
         * remarks : null
         * status : 0
         * gmtCreate : null
         * gmtModified : null
         * orderTime : 2017-11-04 14:37:10.0
         * mark : 0
         * seatName:
         */

        private int shopId;
        private Object shopName;
        private int state;
        private Object logoUrl;
        private int id;
        private String orderNumber;
        private String scheduledName;
        private int seatCount;
        private String scheduledTime;
        private String seatPhone;
        private String accountToken;
        private Object remarks;
        private int status;
        private Object gmtCreate;
        private Object gmtModified;
        private String orderTime;
        private int mark;
        private String seatName;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(Object logoUrl) {
            this.logoUrl = logoUrl;
        }

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

        public String getScheduledName() {
            return scheduledName;
        }

        public void setScheduledName(String scheduledName) {
            this.scheduledName = scheduledName;
        }

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }

        public String getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public String getSeatPhone() {
            return seatPhone;
        }

        public void setSeatPhone(String seatPhone) {
            this.seatPhone = seatPhone;
        }

        public String getAccountToken() {
            return accountToken;
        }

        public void setAccountToken(String accountToken) {
            this.accountToken = accountToken;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(Object gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(Object gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }
    }
}
