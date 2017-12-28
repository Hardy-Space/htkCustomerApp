package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/7/27.
 */

public class WxEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"appId":"wxb872a94f23cc21a0","mchId":"1485611012","prePayId":"wx20170727091554a329875b400452372952","nonceStr":"D8Xv9NSpxBZ6NzN3","timestamp":"2017-07-27 09:15:54","sign":"E31BD56AA7EB9F0997A212ECAE5EFADC447EBE8D19A52CDA6CC7FEC4757168EE","orderNumber":"1707273335210049"}
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
         * appId : wxb872a94f23cc21a0
         * mchId : 1485611012
         * prePayId : wx20170727091554a329875b400452372952
         * nonceStr : D8Xv9NSpxBZ6NzN3
         * timestamp : 2017-07-27 09:15:54
         * sign : E31BD56AA7EB9F0997A212ECAE5EFADC447EBE8D19A52CDA6CC7FEC4757168EE
         * orderNumber : 1707273335210049
         * "orderId":181
         */

        private String appId;
        private String mchId;
        private String prePayId;
        private String nonceStr;
        private String timestamp;
        private String sign;
        private String orderNumber;
        private String orderBody;
        private int orderId;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getPrePayId() {
            return prePayId;
        }

        public void setPrePayId(String prePayId) {
            this.prePayId = prePayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }


        public String getOrderBody() {
            return orderBody;
        }

        public void setOrderBody(String orderBody) {
            this.orderBody = orderBody;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }

    /**
     * 构建extData的实体
     */
    public static class ExtData{
        private String mark;
        private String orderNumber;
        private String orderBody;
        private int orderId;

        public ExtData(String mark, String orderNumber, String orderBody, int orderId) {
            this.mark = mark;
            this.orderNumber = orderNumber;
            this.orderBody = orderBody;
            this.orderId = orderId;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderBody() {
            return orderBody;
        }

        public void setOrderBody(String orderBody) {
            this.orderBody = orderBody;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
