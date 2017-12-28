package com.hl.htk_customer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TuanGouShopPhotoEntity {

    /**
     * code : 100
     * message : 成功
     * data : [{"id":5,"imgUrl":"http://120.27.5.36:8500/htkApp/upload/shop/album/1511104407176.jpg","shopId":1,"flag":0,"accountShopToken":"6be6a1ae-9f7a-4f15-aa63-a28941da8c5a"},{"id":6,"imgUrl":"http://120.27.5.36:8500/htkApp/upload/shop/album/1511104455898.png","shopId":1,"flag":0,"accountShopToken":"6be6a1ae-9f7a-4f15-aa63-a28941da8c5a"},{"id":7,"imgUrl":"http://120.27.5.36:8500/htkApp/upload/shop/album/1511104581551.jpg","shopId":1,"flag":0,"accountShopToken":"6be6a1ae-9f7a-4f15-aa63-a28941da8c5a"}]
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 5
         * imgUrl : http://120.27.5.36:8500/htkApp/upload/shop/album/1511104407176.jpg
         * shopId : 1
         * flag : 0
         * accountShopToken : 6be6a1ae-9f7a-4f15-aa63-a28941da8c5a
         */

        private int id;
        private String imgUrl;
        private int shopId;
        private int flag;
        private String accountShopToken;

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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getAccountShopToken() {
            return accountShopToken;
        }

        public void setAccountShopToken(String accountShopToken) {
            this.accountShopToken = accountShopToken;
        }
    }
}
