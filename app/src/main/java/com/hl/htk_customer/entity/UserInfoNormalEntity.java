package com.hl.htk_customer.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23.
 */

public class UserInfoNormalEntity implements  Parcelable{

    /**
     * code : 100
     * message : 查找用户信息成功
     * data : {"token":"72390b31-5ed2-4204-9c2e-2f8c3086724a","loginWay":1,"nickName":"15","phone":18766242033,"passwordStatus":false,"avaUrl":"http://192.168.1.114:8080/htkApp/upload/app/account/appDefaultAva_img.jpg"}
     */

    private int code;
    private String message;
    private DataBean data;

    protected UserInfoNormalEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<UserInfoNormalEntity> CREATOR = new Creator<UserInfoNormalEntity>() {
        @Override
        public UserInfoNormalEntity createFromParcel(Parcel in) {
            return new UserInfoNormalEntity(in);
        }

        @Override
        public UserInfoNormalEntity[] newArray(int size) {
            return new UserInfoNormalEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

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

    public static class DataBean   implements Serializable{
        /**
         * token : 72390b31-5ed2-4204-9c2e-2f8c3086724a
         * loginWay : 1
         * nickName : 15
         * phone : 18766242033
         * passwordStatus : false
         * avaUrl : http://192.168.1.114:8080/htkApp/upload/app/account/appDefaultAva_img.jpg
         */

        private String token;
        private int loginWay;
        private String nickName;
        private long phone;
        private boolean passwordStatus;
        private String avaUrl;

        private boolean qqStatus;
        private boolean weChatStatus;

        public boolean getQqStatus() {
            return qqStatus;
        }

        public void setQqStatus(boolean qqStatus) {
            this.qqStatus = qqStatus;
        }

        public boolean getWeChatStatus() {
            return weChatStatus;
        }

        public void setWeChatStatus(boolean weChatStatus) {
            this.weChatStatus = weChatStatus;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getLoginWay() {
            return loginWay;
        }

        public void setLoginWay(int loginWay) {
            this.loginWay = loginWay;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public boolean getPasswordStatus() {
            return passwordStatus;
        }

        public void setPasswordStatus(boolean passwordStatus) {
            this.passwordStatus = passwordStatus;
        }

        public String getAvaUrl() {
            return avaUrl;
        }

        public void setAvaUrl(String avaUrl) {
            this.avaUrl = avaUrl;
        }


    }
}
