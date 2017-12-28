package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/11/3.
 */

public class MineEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"avatarUrl":"http://192.168.0.7:8080/htkApp/upload/app/account/1509086334133.jpg","nickName":"version","phone":"18660706071","ticketCount":0,"integralCount":0}
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
         * avatarUrl : http://192.168.0.7:8080/htkApp/upload/app/account/1509086334133.jpg
         * nickName : version
         * phone : 18660706071
         * ticketCount : 0
         * integralCount : 0
         */

        private String avatarUrl;
        private String nickName;
        private String phone;
        private int ticketCount;
        private int integralCount;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public int getIntegralCount() {
            return integralCount;
        }

        public void setIntegralCount(int integralCount) {
            this.integralCount = integralCount;
        }
    }
}
