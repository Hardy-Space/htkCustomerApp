package com.hl.htk_customer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class ShopInfoEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"shopName":"8","logoUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","deliveryFee":20,"score":4.2,"longitude":120.379191,"latitude":36.088469,"monthlySalesVolume":200,"locationAddress":"山东省青岛国际大厦","openingTime":"8:00-23:00","categoryName":"土豪推荐","shopBulletin":{"id":1,"content":"本店联系方式: 0532-80916360,本店的外卖送餐服务实行专有的菜单及价格，外卖产品的具体价格以餐厅外卖页面的公示为准","shopId":1},"shopConsumptionActivities":[{"id":1,"content":"满45减5，满85减12","shopId":1},{"id":2,"content":"下单立省15元！风和日丽","shopId":1},{"id":3,"content":"下单立省22元！欣欣向荣","shopId":1}]}
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

    public static class DataBean implements Serializable {
        /**
         * shopName : 8
         * logoUrl : http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png
         * deliveryFee : 20
         * score : 4.2
         * longitude : 120.379191
         * latitude : 36.088469
         * monthlySalesVolume : 200
         * locationAddress : 山东省青岛国际大厦
         * openingTime : 8:00-23:00
         * categoryName : 土豪推荐
         * shopBulletin : {"id":1,"content":"本店联系方式: 0532-80916360,本店的外卖送餐服务实行专有的菜单及价格，外卖产品的具体价格以餐厅外卖页面的公示为准","shopId":1}
         * shopConsumptionActivities : [{"id":1,"content":"满45减5，满85减12","shopId":1},{"id":2,"content":"下单立省15元！风和日丽","shopId":1},{"id":3,"content":"下单立省22元！欣欣向荣","shopId":1}]
         */

        private String shopName;
        private String logoUrl;
        private double deliveryFee;
        private double score;
        private double longitude;
        private double latitude;
        private int monthlySalesVolume;
        private String locationAddress;
        private String openingTime;
        private String categoryName;
        private ShopBulletinBean shopBulletin;
        private List<ShopBulletinBean> shopConsumptionActivities;
        private int state;  // 0 休息   1 营业


        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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

        public double getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(double deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
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

        public int getMonthlySalesVolume() {
            return monthlySalesVolume;
        }

        public void setMonthlySalesVolume(int monthlySalesVolume) {
            this.monthlySalesVolume = monthlySalesVolume;
        }

        public String getLocationAddress() {
            return locationAddress;
        }

        public void setLocationAddress(String locationAddress) {
            this.locationAddress = locationAddress;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public ShopBulletinBean getShopBulletin() {
            return shopBulletin;
        }

        public void setShopBulletin(ShopBulletinBean shopBulletin) {
            this.shopBulletin = shopBulletin;
        }

        public List<ShopBulletinBean> getShopConsumptionActivities() {
            return shopConsumptionActivities;
        }

        public void setShopConsumptionActivities(List<ShopBulletinBean> shopConsumptionActivities) {
            this.shopConsumptionActivities = shopConsumptionActivities;
        }

        public static class ShopBulletinBean implements Serializable {
            /**
             * id : 1
             * content : 本店联系方式: 0532-80916360,本店的外卖送餐服务实行专有的菜单及价格，外卖产品的具体价格以餐厅外卖页面的公示为准
             * shopId : 1
             */

            private int id;
            private String content;
            private int shopId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }
        }
    }
}
