package com.hl.htk_customer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class WmOrderListEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"orderState":1,"logoUrl":"http://192.168.100.6:8080/htkApp/upload/admin/shopCategory/meishi.png","shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"果盘1","quantity":1,"price":4,"orderId":10},{"productName":"果盘","quantity":1,"price":5,"orderId":10}],"orderAmount":9,"receiptName":"龙威陶","sex":1,"receivingCall":18766242033,"shippingAddress":"山东省青岛市东仲花园","orderNumber":"1706296226510125","paymentMethod":0,"orderTime":"2017-06-29 17:17:53.0"},{"orderState":1,"logoUrl":"http://192.168.100.6:8080/htkApp/upload/admin/shopCategory/meishi.png","shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"甜筒","quantity":1,"price":2,"orderId":11},{"productName":"果盘1","quantity":1,"price":4,"orderId":11}],"orderAmount":6,"receiptName":"龙威陶","sex":1,"receivingCall":18766242033,"shippingAddress":"山东省青岛市东仲花园","orderNumber":"1706296248010033","paymentMethod":0,"orderTime":"2017-06-29 17:21:30.0"}]
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

    public static class DataBean  implements Serializable {
        /**
         * orderState : 1
         * logoUrl : http://192.168.100.6:8080/htkApp/upload/admin/shopCategory/meishi.png
         * shopName : 冰雪怪
         * shopMobilePhone : 15555555555
         * productList : [{"productName":"果盘1","quantity":1,"price":4,"orderId":10},{"productName":"果盘","quantity":1,"price":5,"orderId":10}]
         * orderAmount : 9.0
         * receiptName : 龙威陶
         * sex : 1
         * receivingCall : 18766242033
         * shippingAddress : 山东省青岛市东仲花园
         * orderNumber : 1706296226510125
         * paymentMethod : 0
         * orderTime : 2017-06-29 17:17:53.0
         */
        private int shopId;
        private int orderId;
        private int orderState;
        private String logoUrl;
        private String shopName;
        private String shopMobilePhone;
        private double orderAmount;
        private String receiptName;
        private int sex;
        private long receivingCall;
        private String shippingAddress;
        private String orderNumber;
        private int paymentMethod;
        private String orderTime;
        private List<ProductListBean> productList;
        private String oneProductName;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
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

        public long getReceivingCall() {
            return receivingCall;
        }

        public void setReceivingCall(long receivingCall) {
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

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public String getOneProductName() {
            return oneProductName;
        }

        public void setOneProductName(String oneProductName) {
            this.oneProductName = oneProductName;
        }


        public static class ProductListBean   implements  Serializable{
            /**
             * productName : 果盘1
             * quantity : 1
             * price : 4.0
             * orderId : 10
             */

            private String productName;
            private int quantity;
            private double price;
            private int productId;

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
        }
    }
}
