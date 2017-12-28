package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SeatOrderDetailModel {


    /**
     * code : 100
     * message : 成功
     * data : {"shopId":1,"shopName":null,"state":0,"logoUrl":null,"id":9,"orderNumber":"1711208056010472","scheduledName":"天天","seatCount":2,"seatName":null,"scheduledTime":"2017-11-20 22:22","seatPhone":"18660706071","accountToken":"db5af7ca-e946-483a-8acd-d3b0678a4c8f","remarks":null,"status":0,"gmtCreate":null,"gmtModified":null,"orderTime":"2017-11-20 22:22:40.0","mark":0,"timeLeft":0}
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
         * shopId : 1
         * shopName : null
         * state : 0
         * logoUrl : null
         * id : 9
         * orderNumber : 1711208056010472
         * scheduledName : 天天
         * seatCount : 2
         * seatName : null
         * scheduledTime : 2017-11-20 22:22
         * seatPhone : 18660706071
         * accountToken : db5af7ca-e946-483a-8acd-d3b0678a4c8f
         * remarks : null
         * status : 0
         * gmtCreate : null
         * gmtModified : null
         * orderTime : 2017-11-20 22:22:40.0
         * mark : 0
         * timeLeft : 0
         */

        private String shopId;
        private String shopName;
        private String state;
        private String logoUrl;
        private String id;
        private String orderNumber;
        private String scheduledName;
        private String seatCount;
        private String seatName;
        private String scheduledTime;
        private String seatPhone;
        private String accountToken;
        private String remarks;
        private String status;
        private String gmtCreate;
        private String gmtModified;
        private String orderTime;
        private String mark;
        private String timeLeft;
        private String phone;
        private String address;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(String seatCount) {
            this.seatCount = seatCount;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getTimeLeft() {
            return timeLeft;
        }

        public void setTimeLeft(String timeLeft) {
            this.timeLeft = timeLeft;
        }
    }
}
