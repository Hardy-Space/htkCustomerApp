package com.hl.htk_customer.entity;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class DianCanFenLeiListEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"productName":"甜筒2","price":2,"imgUrl":"htkApp/upload/admin/shopCategory/meishi.png","monthlySalesVolume":100,"categoryId":1},{"id":3,"productName":"甜筒3","price":3,"imgUrl":"htkApp/upload/admin/shopCategory/meishi.png","monthlySalesVolume":100,"categoryId":1}]
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

    public static class DataBean   implements Serializable {
        /**
         * id : 1
         * productName : 甜筒2
         * price : 2.0
         * imgUrl : htkApp/upload/admin/shopCategory/meishi.png
         * monthlySalesVolume : 100
         * categoryId : 1
         */

        private int id;
        private String productName;
        private double price;
        private String imgUrl;
        private int monthlySalesVolume;
        private int categoryId;

        private   int  chooseNum = 0 ;



        public   DataBean(int id,String productName,double price,String imgUrl,int monthlySalesVolume, int categoryId, int  chooseNum ){

            this.id = id;
            this.productName = productName;
            this.price = price;
            this.imgUrl = imgUrl;
            this.monthlySalesVolume = monthlySalesVolume;
            this.categoryId  = categoryId;
            this.chooseNum = chooseNum;

        }


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


        public int getChooseNum() {
            return chooseNum;
        }

        public void setChooseNum(int chooseNum) {
            this.chooseNum = chooseNum;
        }
    }
}
