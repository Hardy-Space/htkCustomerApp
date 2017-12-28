package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ShopAndRecommendEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"shopList":[{"shopId":1,"shopName":"冰雪怪","logoUrl":"http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.379191,"latitude":36.088469,"address":"青岛国际大厦","location":"山东省","intro":"冰雪怪店铺欢迎您 ","score":4.2,"perCapitaPrice":null,"monthlySalesVolume":200,"collection":true,"deliveryFee":null,"categoryName":null,"phone":"0532-88888","mobilePhone":"15555555555","state":1,"mark":0,"accountShopId":1,"shopCategoryId":37,"withDistance":0,"shopJoinTime":null,"shopQrCodeUrl":"htkApp/upload/shop/QRCode/shop_1.png","shopState":1}],"recommendShopList":[{"shopId":3,"shopName":"悦兰亭海鲜自助火锅","logoUrl":"http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.37553,"latitude":36.083944,"address":"青岛万达CBD","location":"山东省","intro":"悦兰亭海鲜自助火锅","score":3.5,"perCapitaPrice":null,"monthlySalesVolume":200,"collection":false,"deliveryFee":null,"categoryName":null,"phone":"0532-88888","mobilePhone":"15555555555","state":1,"mark":0,"accountShopId":3,"shopCategoryId":37,"withDistance":0,"shopJoinTime":null,"shopQrCodeUrl":null,"shopState":1},{"shopId":5,"shopName":"鲜芋仙1","logoUrl":"http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png","openingTime":"8:00-23:00","longitude":120.3795,"latitude":36.088561,"address":"青岛万达CBD","location":"山东省","intro":"鲜芋仙店","score":3.5,"perCapitaPrice":null,"monthlySalesVolume":200,"collection":false,"deliveryFee":null,"categoryName":null,"phone":"0532-88888","mobilePhone":"15555555555","state":1,"mark":0,"accountShopId":5,"shopCategoryId":37,"withDistance":0,"shopJoinTime":null,"shopQrCodeUrl":null,"shopState":1}]}
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
        private List<ShopListBean> shopList;
        private List<RecommendShopListBean> recommendShopList;

        public List<ShopListBean> getShopList() {
            return shopList;
        }

        public void setShopList(List<ShopListBean> shopList) {
            this.shopList = shopList;
        }

        public List<RecommendShopListBean> getRecommendShopList() {
            return recommendShopList;
        }

        public void setRecommendShopList(List<RecommendShopListBean> recommendShopList) {
            this.recommendShopList = recommendShopList;
        }

        public static class ShopListBean {
            /**
             * shopId : 1
             * shopName : 冰雪怪
             * logoUrl : http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png
             * openingTime : 8:00-23:00
             * longitude : 120.379191
             * latitude : 36.088469
             * address : 青岛国际大厦
             * location : 山东省
             * intro : 冰雪怪店铺欢迎您
             * score : 4.2
             * perCapitaPrice : null
             * monthlySalesVolume : 200
             * collection : true
             * deliveryFee : null
             * categoryName : null
             * phone : 0532-88888
             * mobilePhone : 15555555555
             * state : 1
             * mark : 0
             * accountShopId : 1
             * shopCategoryId : 37
             * withDistance : 0.0
             * shopJoinTime : null
             * shopQrCodeUrl : htkApp/upload/shop/QRCode/shop_1.png
             * shopState : 1
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
            private Object perCapitaPrice;
            private int monthlySalesVolume;
            private boolean collection;
            private Object deliveryFee;
            private Object categoryName;
            private String phone;
            private String mobilePhone;
            private int state;
            private int mark;
            private int accountShopId;
            private int shopCategoryId;
            private double withDistance;
            private Object shopJoinTime;
            private String shopQrCodeUrl;
            private int shopState;

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

            public Object getPerCapitaPrice() {
                return perCapitaPrice;
            }

            public void setPerCapitaPrice(Object perCapitaPrice) {
                this.perCapitaPrice = perCapitaPrice;
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

            public Object getDeliveryFee() {
                return deliveryFee;
            }

            public void setDeliveryFee(Object deliveryFee) {
                this.deliveryFee = deliveryFee;
            }

            public Object getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(Object categoryName) {
                this.categoryName = categoryName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
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

            public Object getShopJoinTime() {
                return shopJoinTime;
            }

            public void setShopJoinTime(Object shopJoinTime) {
                this.shopJoinTime = shopJoinTime;
            }

            public String getShopQrCodeUrl() {
                return shopQrCodeUrl;
            }

            public void setShopQrCodeUrl(String shopQrCodeUrl) {
                this.shopQrCodeUrl = shopQrCodeUrl;
            }

            public int getShopState() {
                return shopState;
            }

            public void setShopState(int shopState) {
                this.shopState = shopState;
            }
        }

        public static class RecommendShopListBean {
            /**
             * shopId : 3
             * shopName : 悦兰亭海鲜自助火锅
             * logoUrl : http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png
             * openingTime : 8:00-23:00
             * longitude : 120.37553
             * latitude : 36.083944
             * address : 青岛万达CBD
             * location : 山东省
             * intro : 悦兰亭海鲜自助火锅
             * score : 3.5
             * perCapitaPrice : null
             * monthlySalesVolume : 200
             * collection : false
             * deliveryFee : null
             * categoryName : null
             * phone : 0532-88888
             * mobilePhone : 15555555555
             * state : 1
             * mark : 0
             * accountShopId : 3
             * shopCategoryId : 37
             * withDistance : 0.0
             * shopJoinTime : null
             * shopQrCodeUrl : null
             * shopState : 1
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
            private Object perCapitaPrice;
            private int monthlySalesVolume;
            private boolean collection;
            private Object deliveryFee;
            private Object categoryName;
            private String phone;
            private String mobilePhone;
            private int state;
            private int mark;
            private int accountShopId;
            private int shopCategoryId;
            private double withDistance;
            private Object shopJoinTime;
            private Object shopQrCodeUrl;
            private int shopState;

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

            public Object getPerCapitaPrice() {
                return perCapitaPrice;
            }

            public void setPerCapitaPrice(Object perCapitaPrice) {
                this.perCapitaPrice = perCapitaPrice;
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

            public Object getDeliveryFee() {
                return deliveryFee;
            }

            public void setDeliveryFee(Object deliveryFee) {
                this.deliveryFee = deliveryFee;
            }

            public Object getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(Object categoryName) {
                this.categoryName = categoryName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
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

            public Object getShopJoinTime() {
                return shopJoinTime;
            }

            public void setShopJoinTime(Object shopJoinTime) {
                this.shopJoinTime = shopJoinTime;
            }

            public Object getShopQrCodeUrl() {
                return shopQrCodeUrl;
            }

            public void setShopQrCodeUrl(Object shopQrCodeUrl) {
                this.shopQrCodeUrl = shopQrCodeUrl;
            }

            public int getShopState() {
                return shopState;
            }

            public void setShopState(int shopState) {
                this.shopState = shopState;
            }
        }
    }
}
