package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class TuanGouShopInfoEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"shopName":"鲜芋仙2","logoUrl":null,"deliveryFee":null,"score":3.5,"longitude":null,"latitude":null,"monthlySalesVolume":0,"locationAddress":"山东省青岛万达CBD","openingTime":null,"categoryName":null,"shopBulletin":null,"perCapitaPrice":58,"phone":"0532-88888","collection":false,"shopConsumptionActivities":null,"buyPackageList":[{"id":1,"packageName":"我家牛排","imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/niupai.jpg","usageTime":"08:00 -- 22:00","reservation":false,"price":80,"retailPrice":89,"soldQuantity":200,"shopId":8},{"id":2,"packageName":"圣保罗海鲜涮烤自助","imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/222.jpg","usageTime":"18:00 -- 22:00","reservation":true,"price":120,"retailPrice":188,"soldQuantity":2000,"shopId":8}]}
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
         * shopName : 鲜芋仙2
         * logoUrl : null
         * deliveryFee : null
         * score : 3.5
         * longitude : null
         * latitude : null
         * monthlySalesVolume : 0
         * locationAddress : 山东省青岛万达CBD
         * openingTime : null
         * categoryName : null
         * shopBulletin : null
         * perCapitaPrice : 58.0
         * phone : 0532-88888
         * collection : false
         * shopConsumptionActivities : null
         * buyPackageList : [{"id":1,"packageName":"我家牛排","imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/niupai.jpg","usageTime":"08:00 -- 22:00","reservation":false,"price":80,"retailPrice":89,"soldQuantity":200,"shopId":8},{"id":2,"packageName":"圣保罗海鲜涮烤自助","imgUrl":"http://192.168.100.6:8080/htkApp/upload/shop/advertising/222.jpg","usageTime":"18:00 -- 22:00","reservation":true,"price":120,"retailPrice":188,"soldQuantity":2000,"shopId":8}]
         */

        private String shopName;
        private Object logoUrl;
        private Object deliveryFee;
        private double score;
        private Object longitude;
        private Object latitude;
        private int monthlySalesVolume;
        private String locationAddress;
        private Object openingTime;
        private Object categoryName;
        private Object shopBulletin;
        private double perCapitaPrice;
        private String phone;
        private boolean collection;
        private Object shopConsumptionActivities;
        private List<BuyPackageListBean> buyPackageList;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(Object logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Object getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(Object deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
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

        public Object getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(Object openingTime) {
            this.openingTime = openingTime;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public Object getShopBulletin() {
            return shopBulletin;
        }

        public void setShopBulletin(Object shopBulletin) {
            this.shopBulletin = shopBulletin;
        }

        public double getPerCapitaPrice() {
            return perCapitaPrice;
        }

        public void setPerCapitaPrice(double perCapitaPrice) {
            this.perCapitaPrice = perCapitaPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public boolean isCollection() {
            return collection;
        }

        public void setCollection(boolean collection) {
            this.collection = collection;
        }

        public Object getShopConsumptionActivities() {
            return shopConsumptionActivities;
        }

        public void setShopConsumptionActivities(Object shopConsumptionActivities) {
            this.shopConsumptionActivities = shopConsumptionActivities;
        }

        public List<BuyPackageListBean> getBuyPackageList() {
            return buyPackageList;
        }

        public void setBuyPackageList(List<BuyPackageListBean> buyPackageList) {
            this.buyPackageList = buyPackageList;
        }

        public static class BuyPackageListBean {
            /**
             * id : 1
             * packageName : 我家牛排
             * imgUrl : http://192.168.100.6:8080/htkApp/upload/shop/advertising/niupai.jpg
             * usageTime : 08:00 -- 22:00
             * reservation : false
             * price : 80.0
             * retailPrice : 89.0
             * soldQuantity : 200
             * shopId : 8
             */

            private int id;
            private String packageName;
            private String imgUrl;
            private String usageTime;
            private boolean reservation;
            private double price;
            private double retailPrice;
            private int soldQuantity;
            private int shopId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getUsageTime() {
                return usageTime;
            }

            public void setUsageTime(String usageTime) {
                this.usageTime = usageTime;
            }

            public boolean isReservation() {
                return reservation;
            }

            public void setReservation(boolean reservation) {
                this.reservation = reservation;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(double retailPrice) {
                this.retailPrice = retailPrice;
            }

            public int getSoldQuantity() {
                return soldQuantity;
            }

            public void setSoldQuantity(int soldQuantity) {
                this.soldQuantity = soldQuantity;
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
