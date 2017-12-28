package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DianCanDetailEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"id":1,"productName":"甜筒2","price":2,"imgUrl":"http://192.168.0.9:8080/htkApp/upload/admin/shopCategory/meishi.png","monthlySalesVolume":100,"categoryId":1,"likeQuantity":2,"productDetail":"这是商品介绍，这是商品介绍，这是商品介绍，\n    这是商品介绍，这是商品介绍，这是商品介绍，\n    这是商品介绍，这是商品介绍，这是商品介绍，\n    这是商品介绍，这是商品介绍，这是商品介绍，\n    这是商品介绍，这是商品介绍，这是商品介绍，\n"}
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
         * id : 1
         * productName : 甜筒2
         * price : 2.0
         * imgUrl : http://192.168.0.9:8080/htkApp/upload/admin/shopCategory/meishi.png
         * monthlySalesVolume : 100
         * categoryId : 1
         * likeQuantity : 2
         * productDetail : 这是商品介绍，这是商品介绍，这是商品介绍，
         这是商品介绍，这是商品介绍，这是商品介绍，
         这是商品介绍，这是商品介绍，这是商品介绍，
         这是商品介绍，这是商品介绍，这是商品介绍，
         这是商品介绍，这是商品介绍，这是商品介绍，

         */

        private int id;
        private String productName;
        private double price;
        private String imgUrl;
        private int monthlySalesVolume;
        private int categoryId;
        private int likeQuantity;
        private String productDetail;
        private String shopId;
        private int integral;
        private String description;

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

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
