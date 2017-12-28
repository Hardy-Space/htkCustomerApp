package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class BestShopEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"shopId":1,"shopName":"冰雪怪","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.379191,"latitude":36.088469,"address":"青岛国际大厦","location":"山东省","intro":"冰雪怪店铺欢迎您 ","score":0,"monthlySalesVolume":0,"collection":false,"status":false,"mark":0,"accountShopId":1,"shopCategoryId":8,"withDistance":96},{"shopId":2,"shopName":"鲜芋仙","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.3794,"latitude":36.088551,"address":"青岛万达CBD","location":"山东省","intro":"鲜芋仙店","score":0,"monthlySalesVolume":0,"collection":false,"status":false,"mark":0,"accountShopId":1,"shopCategoryId":8,"withDistance":117},{"shopId":3,"shopName":"悦兰亭海鲜自助火锅","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.37553,"latitude":36.083944,"address":"青岛万达CBD","location":"山东省","intro":"悦兰亭海鲜自助火锅","score":0,"monthlySalesVolume":0,"collection":false,"status":false,"mark":0,"accountShopId":1,"shopCategoryId":8,"withDistance":510},{"shopId":4,"shopName":"海滨干鲜果品","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.364179,"latitude":36.085496,"address":"青岛万达CBD","location":"山东省","intro":"海滨干鲜果品","score":0,"monthlySalesVolume":0,"collection":false,"status":false,"mark":0,"accountShopId":1,"shopCategoryId":8,"withDistance":1303}]
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
         * openingTime : 8:00-23:00
         * longitude : 120.379191
         * latitude : 36.088469
         * address : 青岛国际大厦
         * location : 山东省
         * intro : 冰雪怪店铺欢迎您
         * score : 0.0
         * monthlySalesVolume : 0
         * collection : false
         * status : false
         * mark : 0
         * accountShopId : 1
         * shopCategoryId : 8
         * withDistance : 96.0
         */

        private int shopId;
        private String shopName;
        private String logoUrl;
        private String openingTime;
        private double longitude;
        private double latitude;
        private String address;
        private String location;
        private String intro;
        private double score;
        private int monthlySalesVolume;
        private boolean collection;
        private boolean status;
        private int mark;
        private int accountShopId;
        private int shopCategoryId;
        private double withDistance;

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

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
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

        public int getAccountShopId() {
            return accountShopId;
        }

        public void setAccountShopId(int accountShopId) {
            this.accountShopId = accountShopId;
        }

        public int getShopCategoryId() {
            return shopCategoryId;
        }

        public void setShopCategoryId(int shopCategoryId) {
            this.shopCategoryId = shopCategoryId;
        }

        public double getWithDistance() {
            return withDistance;
        }

        public void setWithDistance(double withDistance) {
            this.withDistance = withDistance;
        }
    }
}
