package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/6/26.
 */

public class EventAddressEntity  {

    public String address = "";

    public boolean loading = false;


    public int toTop = 0;  //  1

    public EventAddressEntity(String address) {
        this.address = address;
    }


    public EventAddressEntity(boolean loading) {
        this.loading = loading;
    }


    public EventAddressEntity(int toTop) {
        this.toTop = toTop;
    }

    public String getAddress() {

        return address;
    }


    public boolean loaded() {
        return loading;
    }


    public int isToTop() {

        return toTop;

    }

}
