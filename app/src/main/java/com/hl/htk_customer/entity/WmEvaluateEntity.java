package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class WmEvaluateEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"list":[{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":1,"commentTime":"2017-08-04 ","content":"啦啦啦","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"挺好吃的","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"看看你天天向上人生路不熟的人类别","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"忌口","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"囖就他么的话费查询余额","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":3,"commentTime":"2017-08-05 ","content":"看篮球","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":4,"commentTime":"2017-08-05 ","content":"饿极了客人多","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":2,"commentTime":"2017-08-05 ","content":"饿了爵","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null}],"count":8}
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
         * list : [{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":1,"commentTime":"2017-08-04 ","content":"啦啦啦","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"挺好吃的","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"看看你天天向上人生路不熟的人类别","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"忌口","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":5,"commentTime":"2017-08-05 ","content":"囖就他么的话费查询余额","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":3,"commentTime":"2017-08-05 ","content":"看篮球","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":4,"commentTime":"2017-08-05 ","content":"饿极了客人多","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null},{"accountId":25,"avaUrl":"http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg","nickName":"Fantery、","commentsStars":2,"commentTime":"2017-08-05 ","content":"饿了爵","accountToken":"ba1cef27-3b9e-4bbe-bbca-f679ece55475","commentId":null}]
         * count : 8
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * accountId : 25
             * avaUrl : http://192.168.0.10:8080/htkApp/upload/app/account/1500946973542.jpg
             * nickName : Fantery、
             * commentsStars : 1.0
             * commentTime : 2017-08-04
             * content : 啦啦啦
             * accountToken : ba1cef27-3b9e-4bbe-bbca-f679ece55475
             * commentId : null
             */

            private int accountId;
            private String avaUrl;
            private String nickName;
            private double commentsStars;
            private String commentTime;
            private String content;
            private String accountToken;
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

            public String getAccountToken() {
                return accountToken;
            }

            public void setAccountToken(String accountToken) {
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
}
