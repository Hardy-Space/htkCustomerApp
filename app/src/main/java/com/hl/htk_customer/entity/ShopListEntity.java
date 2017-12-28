package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class ShopListEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"shopId":1,"shopName":"冰雪怪","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":null,"longitude":120.379191,"latitude":36.088469,"address":null,"location":null,"intro":null,"score":4.2,"monthlySalesVolume":200,"collection":false,"status":false,"mark":0,"accountShopId":null,"shopCategoryId":null}]
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
         * shopId : 1
         * shopName : 冰雪怪
         * logoUrl : http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png
         * openingTime : null
         * longitude : 120.379191
         * latitude : 36.088469
         * address : null
         * location : null
         * intro : null
         * score : 4.2
         * monthlySalesVolume : 200
         * collection : false
         * status : false
         * mark : 0
         * accountShopId : null
         * shopCategoryId : null
         */

        private int shopId;
        private String shopName;
        private String logoUrl;
        private Object openingTime;
        private double longitude;
        private double latitude;
        private Object address;
        private Object location;
        private Object intro;
        private double score;
        private int monthlySalesVolume;
        private boolean collection;
        private boolean status;
        private int mark;
        private Object accountShopId;
        private Object shopCategoryId;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Object getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(Object openingTime) {
            this.openingTime = openingTime;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getIntro() {
            return intro;
        }

        public void setIntro(Object intro) {
            this.intro = intro;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getMonthlySalesVolume() {
            return monthlySalesVolume;
        }

        public void setMonthlySalesVolume(int monthlySalesVolume) {
            this.monthlySalesVolume = monthlySalesVolume;
        }

        public boolean isCollection() {
            return collection;
        }

        public void setCollection(boolean collection) {
            this.collection = collection;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public Object getAccountShopId() {
            return accountShopId;
        }

        public void setAccountShopId(Object accountShopId) {
            this.accountShopId = accountShopId;
        }

        public Object getShopCategoryId() {
            return shopCategoryId;
        }

        public void setShopCategoryId(Object shopCategoryId) {
            this.shopCategoryId = shopCategoryId;
        }
    }
}
