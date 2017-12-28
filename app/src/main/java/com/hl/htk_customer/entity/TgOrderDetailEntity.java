package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class TgOrderDetailEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"orderState":10,"orderId":56,"orderNumber":"1710273211310263","packageId":1,"quantity":1,"paymentMethod":0,"orderTime":"2017-10-27 08:55:13","shopName":"鲜芋仙2","shopId":8,"logoUrl":"http://120.27.5.36:8080/htkApp/upload/admin/shopCategory/meishi.png","packageName":"我家牛排","orderAmount":0.01,"shopPhone":"15555555555","shopAddress":"山东省青岛万达CBD","retailPrice":89,"longitude":120.3797,"latitude":36.088591,"voucherNumber":0,"buyPackageContentList":[{"id":2,"productId":7,"productName":"橙汁","price":0.01,"originalCost":0.01,"quantity":1,"packageId":1}]}
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
         * orderState : 10
         * orderId : 56
         * orderNumber : 1710273211310263
         * packageId : 1
         * quantity : 1
         * paymentMethod : 0
         * orderTime : 2017-10-27 08:55:13
         * shopName : 鲜芋仙2
         * shopId : 8
         * logoUrl : http://120.27.5.36:8080/htkApp/upload/admin/shopCategory/meishi.png
         * packageName : 我家牛排
         * orderAmount : 0.01
         * shopPhone : 15555555555
         * shopAddress : 山东省青岛万达CBD
         * retailPrice : 89.0
         * longitude : 120.3797
         * latitude : 36.088591
         * voucherNumber : 0
         * validityTime :
         * receivingCall :购买人手机号
         * buyPackageContentList : [{"id":2,"productId":7,"productName":"橙汁","price":0.01,"originalCost":0.01,"quantity":1,"packageId":1}]
         */

        private int orderState;
        private int orderId;
        private String orderNumber;
        private int packageId;
        private int quantity;
        private int paymentMethod;
        private String orderTime;
        private String shopName;
        private int shopId;
        private String logoUrl;
        private String packageName;
        private double orderAmount;
        private String shopPhone;
        private String shopAddress;
        private double retailPrice;
        private double longitude;
        private double latitude;
        private int voucherNumber;
        private String validityTime;
        private String userPhone;

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String receivingCall) {
            this.userPhone = receivingCall;
        }

        private List<BuyPackageContentListBean> buyPackageContentList;

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getPackageId() {
            return packageId;
        }

        public void setPackageId(int packageId) {
            this.packageId = packageId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getShopPhone() {
            return shopPhone;
        }

        public void setShopPhone(String shopPhone) {
            this.shopPhone = shopPhone;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public double getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(double retailPrice) {
            this.retailPrice = retailPrice;
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

        public int getVoucherNumber() {
            return voucherNumber;
        }

        public void setVoucherNumber(int voucherNumber) {
            this.voucherNumber = voucherNumber;
        }

        public String getValidityTime() {
            return validityTime;
        }

        public void setValidityTime(String validityTime) {
            this.validityTime = validityTime;
        }

        public List<BuyPackageContentListBean> getBuyPackageContentList() {
            return buyPackageContentList;
        }

        public void setBuyPackageContentList(List<BuyPackageContentListBean> buyPackageContentList) {
            this.buyPackageContentList = buyPackageContentList;
        }

        public static class BuyPackageContentListBean {
            /**
             * id : 2
             * productId : 7
             * productName : 橙汁
             * price : 0.01
             * originalCost : 0.01
             * quantity : 1
             * packageId : 1
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
