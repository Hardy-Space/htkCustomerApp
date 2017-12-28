package com.hl.htk_customer.hldc.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TcBean implements Serializable{
    private String cImgUrl;
    private String cName;
    private int cQuantity;
    private double price;

    public TcBean(){
    }

    public String getImgurl() {
        return cImgUrl;
    }

    public void setImgurl(String imgurl) {
        this.cImgUrl = imgurl;
    }

    public String getName() {
        return cName;
    }

    public void setName(String name) {
        this.cName = name;
    }

    public int getNum() {
        return cQuantity;
    }

    public void setNum(int num) {
        this.cQuantity = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
