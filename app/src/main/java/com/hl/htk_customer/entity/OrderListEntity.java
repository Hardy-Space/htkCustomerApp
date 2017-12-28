package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class OrderListEntity {

    /**
     * code : 100
     * message : 成功
     * data : [{"shopId":1,"shopName":"冰雪怪","state":0,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","id":null,"orderNumber":"1711045763910016","scheduledName":null,"seatCount":6,"seatName":null,"scheduledTime":"2017-10-30 18:00","seatPhone":"18660706071","accountToken":null,"remarks":null,"status":0,"gmtCreate":null,"gmtModified":null,"orderTime":"2017-11-04 16:00:12.0","mark":2},{"shopId":1,"shopName":"冰雪怪","state":0,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","id":null,"orderNumber":"1711045265610070","scheduledName":null,"seatCount":2,"seatName":"Ａ区1座","scheduledTime":"2017-10-25 16:33","seatPhone":"18660706071","accountToken":null,"remarks":null,"status":1,"gmtCreate":null,"gmtModified":null,"orderTime":"2017-11-04 14:37:10.0","mark":2},{"orderId":66,"orderState":2,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","shopId":1,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"葱香鸡米花","quantity":1,"price":0.01,"productId":58,"orderId":66}],"orderAmount":0.02,"receiptName":null,"sex":null,"receivingCall":null,"shippingAddress":null,"orderNumber":"1710303924910080","paymentMethod":0,"orderTime":"2017-10-30 10:54:09","oneProductName":"葱香鸡米花","deliveryFee":null,"commentStatus":null,"mark":0},{"orderId":56,"shopName":"鲜芋仙2","logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/groupBuy/shop_8.jpg","shopId":8,"orderTime":"2017-10-27 08:55:13","orderState":10,"orderAmount":0.01,"packageName":"我家牛排","packageId":1,"orderNumber":"1710273211310263","voucherNumber":0,"mark":1},{"orderId":55,"orderState":1,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","shopId":1,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"葱香鸡米花","quantity":1,"price":0.01,"productId":58,"orderId":55}],"orderAmount":0.02,"receiptName":null,"sex":null,"receivingCall":null,"shippingAddress":null,"orderNumber":"1710266936410211","paymentMethod":0,"orderTime":"2017-10-26 19:16:04","oneProductName":"葱香鸡米花","deliveryFee":null,"commentStatus":null,"mark":0},{"orderId":54,"orderState":5,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","shopId":1,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"葱香鸡米花","quantity":1,"price":0.01,"productId":58,"orderId":54}],"orderAmount":0.02,"receiptName":null,"sex":null,"receivingCall":null,"shippingAddress":null,"orderNumber":"1710266910510097","paymentMethod":0,"orderTime":"2017-10-26 19:11:45","oneProductName":"葱香鸡米花","deliveryFee":null,"commentStatus":null,"mark":0},{"orderId":53,"orderState":5,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","shopId":1,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"葱香鸡米花","quantity":1,"price":0.01,"productId":58,"orderId":53}],"orderAmount":0.02,"receiptName":null,"sex":null,"receivingCall":null,"shippingAddress":null,"orderNumber":"1710266845010013","paymentMethod":0,"orderTime":"2017-10-26 19:00:50","oneProductName":"葱香鸡米花","deliveryFee":null,"commentStatus":null,"mark":0},{"orderId":52,"orderState":1,"logoUrl":"http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg","shopId":1,"shopName":"冰雪怪","shopMobilePhone":"15555555555","productList":[{"productName":"麻辣鸡米花","quantity":1,"price":0.01,"productId":57,"orderId":52}],"orderAmount":0.02,"receiptName":null,"sex":null,"receivingCall":null,"shippingAddress":null,"orderNumber":"1710266324810578","paymentMethod":0,"orderTime":"2017-10-26 17:34:08","oneProductName":"麻辣鸡米花","deliveryFee":null,"commentStatus":null,"mark":0}]
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
         * state : 0
         * logoUrl : http://192.168.0.7:8080/htkApp/upload/shop/takeout/shop_1.jpg
         * id : null
         * orderNumber : 1711045763910016
         * scheduledName : null
         * seatCount : 6
         * seatName : null
         * scheduledTime : 2017-10-30 18:00
         * seatPhone : 18660706071
         * accountToken : null
         * remarks : null
         * status : 0
         * gmtCreate : null
         * gmtModified : null
         * orderTime : 2017-11-04 16:00:12.0
         * mark : 2
         * orderId : 66
         * orderState : 2
         * shopMobilePhone : 15555555555
         * productList : [{"productName":"葱香鸡米花","quantity":1,"price":0.01,"productId":58,"orderId":66}]
         * orderAmount : 0.02
         * receiptName : null
         * sex : null
         * receivingCall : null
         * shippingAddress : null
         * paymentMethod : 0
         * oneProductName : 葱香鸡米花
         * deliveryFee : null
         * commentStatus : null
         * packageName : 我家牛排
         * packageId : 1
         * voucherNumber : 0
         */

        private int shopId;
        private String shopName;
        private int state;
        private String logoUrl;
        private Object id;
        private String orderNumber;
        private Object scheduledName;
        private int seatCount;
        private String seatName;
        private String scheduledTime;
        private String seatPhone;
        private Object accountToken;
        private Object remarks;
        private int status;
        private Object gmtCreate;
        private Object gmtModified;
        private String orderTime;
        private int mark;
        private int orderId;
        private int orderState;
        private String shopMobilePhone;
        private double orderAmount;
        private Object receiptName;
        private Object sex;
        private Object receivingCall;
        private Object shippingAddress;
        private int paymentMethod;
        private String oneProductName;
        private Object deliveryFee;
        private Object commentStatus;
        private String packageName;
        private int packageId;
        private int voucherNumber;
        private List<ProductListBean> productList;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Object getScheduledName() {
            return scheduledName;
        }

        public void setScheduledName(Object scheduledName) {
            this.scheduledName = scheduledName;
        }

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public String getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public String getSeatPhone() {
            return seatPhone;
        }

        public void setSeatPhone(String seatPhone) {
            this.seatPhone = seatPhone;
        }

        public Object getAccountToken() {
            return accountToken;
        }

        public void setAccountToken(Object accountToken) {
            this.accountToken = accountToken;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(Object gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(Object gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
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

        public Object getReceiptName() {
            return receiptName;
        }

        public void setReceiptName(Object receiptName) {
            this.receiptName = receiptName;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getReceivingCall() {
            return receivingCall;
        }

        public void setReceivingCall(Object receivingCall) {
            this.receivingCall = receivingCall;
        }

        public Object getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(Object shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOneProductName() {
            return oneProductName;
        }

        public void setOneProductName(String oneProductName) {
            this.oneProductName = oneProductName;
        }

        public Object getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(Object deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public Object getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(Object commentStatus) {
            this.commentStatus = commentStatus;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getPackageId() {
            return packageId;
        }

        public void setPackageId(int packageId) {
            this.packageId = packageId;
        }

        public int getVoucherNumber() {
            return voucherNumber;
        }

        public void setVoucherNumber(int voucherNumber) {
            this.voucherNumber = voucherNumber;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * productName : 葱香鸡米花
             * quantity : 1
             * price : 0.01
             * productId : 58
             * orderId : 66
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
