package com.hl.htk_customer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/24.
 */

public class AddressListEntity {


    /**
     * code : 100
     * message : 获得用户外卖收货地址成功
     * data : [{"addressId":1000,"userName":"张三","sex":"先生","phone":18660706070,"location":"山东省青岛市","address":"市北区延吉路万达商务楼B座820","token":"da7e6e5b-7567-4576-a524-f10eeb1d2849","gmtCreate":null,"gmtModified":null},{"addressId":1001,"userName":"张四","sex":"先生","phone":18660706060,"location":"山东省青岛市","address":"市北区延吉路卓越大厦","token":"da7e6e5b-7567-4576-a524-f10eeb1d2849","gmtCreate":null,"gmtModified":null}]
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

    public static class DataBean  implements Serializable {
        /**
         * addressId : 1000
         * userName : 张三
         * sex : 先生
         * phone : 18660706070
         * location : 山东省青岛市
         * address : 市北区延吉路万达商务楼B座820
         * token : da7e6e5b-7567-4576-a524-f10eeb1d2849
         * gmtCreate : null
         * gmtModified : null
         */

        private int addressId;
        private String userName;
        private int sex;
        private long phone;
        private String location;
        private String address;
        private String token;
        private Object gmtCreate;
        private Object gmtModified;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
    }
}
