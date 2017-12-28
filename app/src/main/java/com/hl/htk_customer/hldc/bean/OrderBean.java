package com.hl.htk_customer.hldc.bean;

/**
 * Created by Administrator on 2017/11/1.
 */

public class OrderBean {
    private String orderno;
    private String date;
    private String zhuangtai;
    private String time;
    private String num;
    private String price;

    public OrderBean(String orderno, String date, String zhuangtai, String time, String num, String price) {
        this.orderno = orderno;
        this.date = date;
        this.zhuangtai = zhuangtai;
        this.time = time;
        this.num = num;
        this.price = price;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
