package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class SeatEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"seatName":"Ａ区1座","numberSeat":4,"shopId":1,"seatStatus":0},{"id":2,"seatName":"Ａ区2座","numberSeat":4,"shopId":1,"seatStatus":0},{"id":3,"seatName":"Ａ区3座","numberSeat":4,"shopId":1,"seatStatus":0},{"id":4,"seatName":"Ａ区4座","numberSeat":4,"shopId":1,"seatStatus":0}]
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
         * id : 1
         * seatName : Ａ区1座
         * numberSeat : 4
         * shopId : 1
         * seatStatus : 0
         */

        private int id;
        private String seatName;
        private int numberSeat;
        private int shopId;
        private int seatStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public int getNumberSeat() {
            return numberSeat;
        }

        public void setNumberSeat(int numberSeat) {
            this.numberSeat = numberSeat;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getSeatStatus() {
            return seatStatus;
        }

        public void setSeatStatus(int seatStatus) {
            this.seatStatus = seatStatus;
        }
    }
}
