package com.hl.htk_customer.activity.buffetmeal;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class BuffetMealGoodsInfo {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":12,"productName":"蔬菜沙拉","price":20,"imgUrl":"http://192.168.0.7:8080/htkApp/upload/shop/buffetFood/1508740180060.jpg","monthlySalesVolume":100,"categoryId":9,"likeQuantity":0,"productDetail":"","shopId":null,"integral":10,"description":null},{"id":13,"productName":"蔬菜沙拉小份","price":0.01,"imgUrl":"http://192.168.0.7:8080/htkApp/upload/shop/buffetFood/1508740180060.jpg","monthlySalesVolume":100,"categoryId":9,"likeQuantity":0,"productDetail":"","shopId":null,"integral":1,"description":null}]
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
         * id : 12
         * productName : 蔬菜沙拉
         * price : 20.0
         * imgUrl : http://192.168.0.7:8080/htkApp/upload/shop/buffetFood/1508740180060.jpg
         * monthlySalesVolume : 100
         * categoryId : 9
         * likeQuantity : 0
         * productDetail :
         * shopId : null
         * integral : 10
         * description : null
         */

        private int id;
        private String productName;
        private double price;
        private String imgUrl;
        private int monthlySalesVolume;
        private int categoryId;
        private int likeQuantity;
        private String productDetail;
        private Object shopId;
        private int integral;
        private Object description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getMonthlySalesVolume() {
            return monthlySalesVolume;
        }

        public void setMonthlySalesVolume(int monthlySalesVolume) {
            this.monthlySalesVolume = monthlySalesVolume;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getLikeQuantity() {
            return likeQuantity;
        }

        public void setLikeQuantity(int likeQuantity) {
            this.likeQuantity = likeQuantity;
        }

        public String getProductDetail() {
            return productDetail;
        }

        public void setProductDetail(String productDetail) {
            this.productDetail = productDetail;
        }

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }
    }
}
