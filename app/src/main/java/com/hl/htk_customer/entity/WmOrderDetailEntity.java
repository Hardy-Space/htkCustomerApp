package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class WmOrderDetailEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"orderId":null,"orderState":4,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/admin/shopCategory/meishi.png","shopId":null,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"测试商品","quantity":1,"price":0.01,"productId":55,"orderId":139},{"productName":"测试商品","quantity":1,"price":0.01,"productId":55,"orderId":139}],"orderAmount":0.02,"receiptName":"王","sex":1,"receivingCall":"15856563566","shippingAddress":"过来看看卡拉卡拉","orderNumber":"1710126086110084","paymentMethod":0,"orderTime":"2017-10-12 16:54:32","oneProductName":null,"deliveryFee":0.01,"commentStatus":0,"mark":0}
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
         * orderId : null
         * orderState : 4
         * logoUrl : http://192.168.0.7:8080/htkApp/upload/admin/shopCategory/meishi.png
         * shopId : null
         * shopName : 冰雪怪
         * shopMobilePhone : 15555555555
         * productList : [{"productName":"测试商品","quantity":1,"price":0.01,"productId":55,"orderId":139},{"productName":"测试商品","quantity":1,"price":0.01,"productId":55,"orderId":139}]
         * orderAmount : 0.02
         * receiptName : 王
         * sex : 1
         * receivingCall : 15856563566
         * shippingAddress : 过来看看卡拉卡拉
         * orderNumber : 1710126086110084
         * paymentMethod : 0
         * orderTime : 2017-10-12 16:54:32
         * oneProductName : null
         * deliveryFee : 0.01
         * commentStatus : 0
         * mark : 0
         * timeLeft：
         */

        private Object orderId;
        private int orderState;
        private String logoUrl;
        private int shopId;
        private String shopName;
        private String shopMobilePhone;
        private double orderAmount;
        private String receiptName;
        private int sex;
        private String receivingCall;
        private String shippingAddress;
        private String orderNumber;
        private int paymentMethod;
        private String orderTime;
        private Object oneProductName;
        private double deliveryFee;
        private int commentStatus;
        private int mark;
        private long timeLeft;
        private List<ProductListBean> productList;

        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

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

        public String getShopMobilePhone() {
            return shopMobilePhone;
        }

        public void setShopMobilePhone(String shopMobilePhone) {
            this.shopMobilePhone = shopMobilePhone;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getReceiptName() {
            return receiptName;
        }

        public void setReceiptName(String receiptName) {
            this.receiptName = receiptName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getReceivingCall() {
            return receivingCall;
        }

        public void setReceivingCall(String receivingCall) {
            this.receivingCall = receivingCall;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
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

        public Object getOneProductName() {
            return oneProductName;
        }

        public void setOneProductName(Object oneProductName) {
            this.oneProductName = oneProductName;
        }

        public double getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(double deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public int getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(int commentStatus) {
            this.commentStatus = commentStatus;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public long getTimeLeft() {
            return timeLeft;
        }

        public void setTimeLeft(long timeLeft) {
            this.timeLeft = timeLeft;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * productName : 测试商品
             * quantity : 1
             * price : 0.01
             * productId : 55
             * orderId : 139
             */

            private String productName;
            private int quantity;
            private double price;
            private int productId;
            private int orderId;

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
        }
    }
}
