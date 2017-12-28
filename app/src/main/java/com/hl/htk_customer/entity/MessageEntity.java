package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class MessageEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"noticeTitle":"您的订单已下单成功","noticeContent":"您在『冰雪怪』下的订单已下单成功   (点击查看详情)","noticeTime":"2017-07-04 14:40:16.0","orderId":29,"shopId":1,"mark":0}]
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
         * noticeTitle : 您的订单已下单成功
         * noticeContent : 您在『冰雪怪』下的订单已下单成功   (点击查看详情)
         * noticeTime : 2017-07-04 14:40:16.0
         * orderId : 29
         * shopId : 1
         * mark : 0
         */

        private String noticeTitle;
        private String noticeContent;
        private String noticeTime;
        private int orderId;
        private int shopId;
        private int mark;

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getNoticeTime() {
            return noticeTime;
        }

        public void setNoticeTime(String noticeTime) {
            this.noticeTime = noticeTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }
    }
}
