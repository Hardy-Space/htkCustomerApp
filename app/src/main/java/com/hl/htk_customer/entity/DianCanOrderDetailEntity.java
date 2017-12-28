package com.hl.htk_customer.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.Loader;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DianCanOrderDetailEntity implements Serializable {


    /**
     * code : 100
     * message : ，成功
     * data : {"id":3,"shopName":"冰雪怪","shopId":1,"logoUrl":"http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png","payState":1,"seatName":"Ａ区1座","orderAmount":2,"paymentMethod":0,"orderTime":"08-01 15:44","productLists":[{"id":null,"productId":null,"productName":"甜筒2","quantity":1,"price":2,"orderId":null}]}
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
         * id : 3
         * shopName : 冰雪怪
         * shopId : 1
         * logoUrl : http://192.168.0.10:8080/htkApp/upload/admin/shopCategory/meishi.png
         * payState : 1
         * seatName : Ａ区1座
         * orderAmount : 2.0
         * paymentMethod : 0
         * orderTime : 08-01 15:44
         * productLists : [{"id":null,"productId":null,"productName":"甜筒2","quantity":1,"price":2,"orderId":null}]
         * int orderState
         */

        private String orderNumber;
        private int id;
        private String shopName;
        private int shopId;
        private String logoUrl;
        private int payState;
        private String seatName;
        private double orderAmount;
        private int paymentMethod;
        private String orderTime;
        private List<ProductListsBean> productLists;
        private int orderState;

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
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

        public List<ProductListsBean> getProductLists() {
            return productLists;
        }

        public void setProductLists(List<ProductListsBean> productLists) {
            this.productLists = productLists;
        }

        public static class ProductListsBean implements Parcelable {
            /**
             * id : null
             * productId : null
             * productName : 甜筒2
             * quantity : 1
             * price : 2.0
             * orderId : null
             */

            private int id;
            private int productId;
            private String productName;
            private int quantity;
            private double price;
            private int orderId;
        //    private  int  payState;
            private ProductListsBean bean;


            private   boolean  isSelected ;


            public ProductListsBean(int id, String productName, int quantity, double price , boolean isSelected) {

                this.id = id;
                this.productName = productName;
                this.quantity = quantity;
                this.price = price;
                this.isSelected = false;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public ProductListsBean getBean() {
                return bean;
            }

            public void setBean(ProductListsBean bean) {
                this.bean = bean;
            }

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

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {


                dest.writeInt(id);
                dest.writeInt(productId);
                dest.writeString(productName);
                dest.writeInt(quantity);
                dest.writeDouble(price);
                dest.writeInt(orderId);
                dest.writeParcelable(bean, 0);

            }


            public static final Parcelable.Creator<ProductListsBean> CREATOR = new Parcelable.Creator<ProductListsBean>() {

                @Override
                public ProductListsBean createFromParcel(Parcel source) {
                    return new ProductListsBean(source);
                }

                @Override
                public ProductListsBean[] newArray(int size) {
                    return new ProductListsBean[size];
                }
            };


            private ProductListsBean(Parcel in) {


             /*   private int id;
                private int productId;
                private String productName;
                private int quantity;
                private double price;
                private int orderId;
                private ProductListsBean bean;*/

                id = in.readInt();
                productId = in.readInt();
                productName = in.readString();
                quantity = in.readInt();
                price = in.readDouble();
                orderId = in.readInt();
                bean = in.readParcelable(Thread.currentThread().getContextClassLoader());


            }


        }
    }
}
