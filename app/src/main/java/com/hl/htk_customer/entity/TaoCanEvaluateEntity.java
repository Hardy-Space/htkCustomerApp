package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */

public class TaoCanEvaluateEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"accountId":22,"avaUrl":"http://192.168.100.6:8080/htkApp/upload/app/account/1498558641850.jpg","nickName":"拖小JJ","commentsStars":5,"commentTime":"2017-06-30 ","content":"不错 ^ v ^ ","accountToken":null,"commentId":null}]
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
         * accountId : 22
         * avaUrl : http://192.168.100.6:8080/htkApp/upload/app/account/1498558641850.jpg
         * nickName : 拖小JJ
         * commentsStars : 5.0
         * commentTime : 2017-06-30
         * content : 不错 ^ v ^
         * accountToken : null
         * commentId : null
         */

        private int accountId;
        private String avaUrl;
        private String nickName;
        private double commentsStars;
        private String commentTime;
        private String content;
        private Object accountToken;
        private Object commentId;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getAvaUrl() {
            return avaUrl;
        }

        public void setAvaUrl(String avaUrl) {
            this.avaUrl = avaUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getCommentsStars() {
            return commentsStars;
        }

        public void setCommentsStars(double commentsStars) {
            this.commentsStars = commentsStars;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getAccountToken() {
            return accountToken;
        }

        public void setAccountToken(Object accountToken) {
            this.accountToken = accountToken;
        }

        public Object getCommentId() {
            return commentId;
        }

        public void setCommentId(Object commentId) {
            this.commentId = commentId;
        }
    }
}
