package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/6/30.
 */

public class EvaluateDetialEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"id":null,"commentsStars":4,"content":"好吃","shopId":null,"gmtCreate":null,"comment":true}
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
         * id : null
         * commentsStars : 4.0
         * content : 好吃
         * shopId : null
         * gmtCreate : null
         * comment : true
         */

        private Object id;
        private double commentsStars;
        private String content;
        private Object shopId;
        private Object gmtCreate;
        private boolean comment;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public double getCommentsStars() {
            return commentsStars;
        }

        public void setCommentsStars(double commentsStars) {
            this.commentsStars = commentsStars;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public Object getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(Object gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public boolean isComment() {
            return comment;
        }

        public void setComment(boolean comment) {
            this.comment = comment;
        }
    }
}
