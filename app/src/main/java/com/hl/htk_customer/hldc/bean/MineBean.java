package com.hl.htk_customer.hldc.bean;

/**
 * Created by asus on 2017/11/2.
 */

public class MineBean {
    private String imgUrl; // 头像url
    private String nickName; //呢称
    private	String userPhone; //手机号
    private	int  integralVal; //积分值
    private	int discountCouponCount; //优惠券数量

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getIntegralVal() {
        return integralVal;
    }

    public void setIntegralVal(int integralVal) {
        this.integralVal = integralVal;
    }

    public int getDiscountCouponCount() {
        return discountCouponCount;
    }

    public void setDiscountCouponCount(int discountCouponCount) {
        this.discountCouponCount = discountCouponCount;
    }
}
