package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/9/27.
 */

public class CustomizeMsgEntity {

    /**
     * {"orderNumber":"1710207429110179","flag":0,"orderId":176,"orderState":0}
     */

    private String orderNumber;
    private int orderState;
    private int flag;
    private int orderId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
