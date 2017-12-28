package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */

public class TaoCanDetailEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"shopName":"鲜芋仙","longitude":120.3794,"latitude":36.088551,"locationAddress":"山东省青岛万达CBD","commentCount":null,"phone":"0532-88888","buyPackage":{"id":7,"packageName":"火锅套餐","imgUrl":"http://192.168.0.7:8080/htkApp/upload/shop/groupBuy/1509094696876.jpg","usageTime":"2017-10-01 到 2017-10-27","reservation":false,"price":0.01,"retailPrice":0.01,"soldQuantity":0,"score":3,"useDetails":"团购详情","useRules":"无需预约，消费高峰时可能需要等位","peopleUsedNumber":5,"shopId":2},"buyPackageContentList":[{"id":1,"productId":7,"productName":"橙汁","price":0.01,"originalCost":0.01,"quantity":1,"packageId":7}]}
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
         * shopName : 鲜芋仙
         * longitude : 120.3794
         * latitude : 36.088551
         * locationAddress : 山东省青岛万达CBD
         * commentCount : null
         * phone : 0532-88888
         * buyPackage : {"id":7,"packageName":"火锅套餐","imgUrl":"http://192.168.0.7:8080/htkApp/upload/shop/groupBuy/1509094696876.jpg","usageTime":"2017-10-01 到 2017-10-27","reservation":false,"price":0.01,"retailPrice":0.01,"soldQuantity":0,"score":3,"useDetails":"团购详情","useRules":"无需预约，消费高峰时可能需要等位","peopleUsedNumber":5,"shopId":2}
         * buyPackageContentList : [{"id":1,"productId":7,"productName":"橙汁","price":0.01,"originalCost":0.01,"quantity":1,"packageId":7}]
         */

        private String shopName;
        private double longitude;
        private double latitude;
        private String locationAddress;
        private Object commentCount;
        private String phone;
        private BuyPackageBean buyPackage;
        private List<BuyPackageContentListBean> buyPackageContentList;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
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

        public String getLocationAddress() {
            return locationAddress;
        }

        public void setLocationAddress(String locationAddress) {
            this.locationAddress = locationAddress;
        }

        public Object getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Object commentCount) {
            this.commentCount = commentCount;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public BuyPackageBean getBuyPackage() {
            return buyPackage;
        }

        public void setBuyPackage(BuyPackageBean buyPackage) {
            this.buyPackage = buyPackage;
        }

        public List<BuyPackageContentListBean> getBuyPackageContentList() {
            return buyPackageContentList;
        }

        public void setBuyPackageContentList(List<BuyPackageContentListBean> buyPackageContentList) {
            this.buyPackageContentList = buyPackageContentList;
        }

        public static class BuyPackageBean {
            /**
             * id : 7
             * packageName : 火锅套餐
             * imgUrl : http://192.168.0.7:8080/htkApp/upload/shop/groupBuy/1509094696876.jpg
             * usageTime : 2017-10-01 到 2017-10-27
             * reservation : false
             * price : 0.01
             * retailPrice : 0.01
             * soldQuantity : 0
             * score : 3.0
             * useDetails : 团购详情
             * useRules : 无需预约，消费高峰时可能需要等位
             * peopleUsedNumber : 5
             * shopId : 2
             */

            private int id;
            private String packageName;
            private String imgUrl;
            private String usageTime;
            private boolean reservation;
            private double price;
            private double retailPrice;
            private int soldQuantity;
            private double score;
            private String useDetails;
            private String useRules;
            private int peopleUsedNumber;
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

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getUseDetails() {
                return useDetails;
            }

            public void setUseDetails(String useDetails) {
                this.useDetails = useDetails;
            }

            public String getUseRules() {
                return useRules;
            }

            public void setUseRules(String useRules) {
                this.useRules = useRules;
            }

            public int getPeopleUsedNumber() {
                return peopleUsedNumber;
            }

            public void setPeopleUsedNumber(int peopleUsedNumber) {
                this.peopleUsedNumber = peopleUsedNumber;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }
        }

        public static class BuyPackageContentListBean {
            /**
             * id : 1
             * productId : 7
             * productName : 橙汁
             * price : 0.01
             * originalCost : 0.01
             * quantity : 1
             * packageId : 7
             */

            private int id;
            private int productId;
            private String productName;
            private double price;
            private double originalCost;
            private int quantity;
            private int packageId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getOriginalCost() {
                return originalCost;
            }

            public void setOriginalCost(double originalCost) {
                this.originalCost = originalCost;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getPackageId() {
                return packageId;
            }

            public void setPackageId(int packageId) {
                this.packageId = packageId;
            }
        }
    }
}
