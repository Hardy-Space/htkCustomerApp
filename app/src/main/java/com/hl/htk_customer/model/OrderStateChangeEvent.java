package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/9/27.
 */

public class OrderStateChangeEvent {

    private String msg;

    public OrderStateChangeEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
