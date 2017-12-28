package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class CouponEntity {
    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"tName":"冰雪怪店铺专用优惠券","tExpiration":"2017-12-05 22:00:00","tUsePhone":"18660706071","tMoney":5,"tUseMoney":18}]
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
         * id : 1
         * tName : 冰雪怪店铺专用优惠券
         * tExpiration : 2017-12-05 22:00:00
         * tUsePhone : 18660706071
         * tMoney : 5.0
         * tUseMoney : 18.0
         */

        private int id;
        private String tName;
        private String tExpiration;
        private String tUsePhone;
        private double tMoney;
        private double tUseMoney;
        private boolean isSelect;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTName() {
            return tName;
        }

        public void setTName(String tName) {
            this.tName = tName;
        }

        public String getTExpiration() {
            return tExpiration;
        }

        public void setTExpiration(String tExpiration) {
            this.tExpiration = tExpiration;
        }

        public String getTUsePhone() {
            return tUsePhone;
        }

        public void setTUsePhone(String tUsePhone) {
            this.tUsePhone = tUsePhone;
        }

        public double getTMoney() {
            return tMoney;
        }

        public void setTMoney(double tMoney) {
            this.tMoney = tMoney;
        }

        public double getTUseMoney() {
            return tUseMoney;
        }

        public void setTUseMoney(double tUseMoney) {
            this.tUseMoney = tUseMoney;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }

}
