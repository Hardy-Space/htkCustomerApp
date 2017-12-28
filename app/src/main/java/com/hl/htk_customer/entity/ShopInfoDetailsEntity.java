package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/11/4.
 */

public class ShopInfoDetailsEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"score":4.2,"deliveryFee":0.01,"phone":null,"openingTime":null,"latitude":36.088469,"monthlySalesVolume":200,"shopName":"冰雪怪","locationAddress":"山东省青岛国际大厦","shopBulletin":{"id":1,"content":"本店联系方式: 0532-80916360,本店的外卖送餐服务实行专有的菜单及价格，外卖产品的具体价格以餐厅外卖页面的公示为准","shopId":1},"longitude":120.379191}
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
         * score : 4.2
         * deliveryFee : 0.01
         * phone : null
         * openingTime : null
         * latitude : 36.088469
         * monthlySalesVolume : 200
         * shopName : 冰雪怪
         * locationAddress : 山东省青岛国际大厦
         * shopBulletin : {"id":1,"content":"本店联系方式: 0532-80916360,本店的外卖送餐服务实行专有的菜单及价格，外卖产品的具体价格以餐厅外卖页面的公示为准","shopId":1}
         * longitude : 120.379191
         */

        private double score;
        private double deliveryFee;
        private Object phone;
        private Object openingTime;
        private double latitude;
        private int monthlySalesVolume;
        private String shopName;
        private String locationAddress;
        private ShopBulletinBean shopBulletin;
        private double longitude;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(double deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(Object openingTime) {
            this.openingTime = openingTime;
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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLocationAddress() {
            return locationAddress;
        }

        public void setLocationAddress(String locationAddress) {
            this.locationAddress = locationAddress;
        }

        public ShopBulletinBean getShopBulletin() {
            return shopBulletin;
        }

        public void setShopBulletin(ShopBulletinBean shopBulletin) {
            this.shopBulletin = shopBulletin;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public static class ShopBulletinBean {
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
