package com.hl.htk_customer.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by yinqilei on 17-6-29.
 * 订单记录实体类
 */
public class OrderRecord {

    private Integer id;

    private String orderNumber;  //订单号

    private Double orderAmount;  //订单金额

    private String receiptName;  //收货人姓名

    private Long receivingCall;  //收货电话

    private String shippingAddress;  //收货地址

    private String orderBody;  //订单内容

    private Integer paymentMethod;   //0微信    1支付宝

    private String remark;   //备注

    private Integer orderState;  //订单状态  1:用户下单成功   2:商家接单成功   3:派送中   4:用户收货成功 5取消订单

    private List<OrderProduct> productList; //产品集合

    private String accountToken;  //app用户token

    private Integer shopId;   //店铺id

    private String orderTime;   //下单时间

    private int sex;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public Long getReceivingCall() {
        return receivingCall;
    }

    public void setReceivingCall(Long receivingCall) {
        this.receivingCall = receivingCall;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}


