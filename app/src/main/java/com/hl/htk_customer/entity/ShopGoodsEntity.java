package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ShopGoodsEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":6,"categoryName":"冷饮","takeoutProductList":[{"id":24,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"甜筒","monthlySalesVolume":20,"favorableRate":4.2,"price":2,"categoryId":6,"shopId":1},{"id":26,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"甜筒1","monthlySalesVolume":20,"favorableRate":4.2,"price":3,"categoryId":6,"shopId":1}]},{"id":7,"categoryName":"小吃","takeoutProductList":[{"id":25,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"果盘","monthlySalesVolume":20,"favorableRate":4.2,"price":5,"categoryId":7,"shopId":1},{"id":27,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"果盘1","monthlySalesVolume":20,"favorableRate":4.2,"price":4,"categoryId":7,"shopId":1}]}]
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
         * id : 6
         * categoryName : 冷饮
         * takeoutProductList : [{"id":24,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"甜筒","monthlySalesVolume":20,"favorableRate":4.2,"price":2,"categoryId":6,"shopId":1},{"id":26,"imgUrl":"http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png","productName":"甜筒1","monthlySalesVolume":20,"favorableRate":4.2,"price":3,"categoryId":6,"shopId":1}]
         */

        private int id;
        private String categoryName;
        private List<TakeoutProductListBean> takeoutProductList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<TakeoutProductListBean> getTakeoutProductList() {
            return takeoutProductList;
        }

        public void setTakeoutProductList(List<TakeoutProductListBean> takeoutProductList) {
            this.takeoutProductList = takeoutProductList;
        }

        public static class TakeoutProductListBean {
            /**
             * id : 24
             * imgUrl : http://192.168.1.105:8080/htkApp/upload/admin/shopCategory/meishi.png
             * productName : 甜筒
             * monthlySalesVolume : 20
             * favorableRate : 4.2
             * price : 2.0
             * categoryId : 6
             * shopId : 1
             */

            private int id;
            private String imgUrl;
            private String productName;
            private int monthlySalesVolume;
            private double favorableRate;
            private double price;
            private int categoryId;
            private int shopId;
            private  int inventory;
            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getInventory() {
                return inventory;
            }

            public void setInventory(int inventory) {
                this.inventory = inventory;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getMonthlySalesVolume() {
                return monthlySalesVolume;
            }

            public void setMonthlySalesVolume(int monthlySalesVolume) {
                this.monthlySalesVolume = monthlySalesVolume;
            }

            public double getFavorableRate() {
                return favorableRate;
            }

            public void setFavorableRate(double favorableRate) {
                this.favorableRate = favorableRate;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
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
