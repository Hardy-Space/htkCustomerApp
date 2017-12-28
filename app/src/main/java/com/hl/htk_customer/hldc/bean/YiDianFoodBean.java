package com.hl.htk_customer.hldc.bean;


/**
 * Created by asus on 2017/11/3.
 */
public class YiDianFoodBean {

    private Integer id;
    private Integer productId; //产品id
    private String productName; //产品名字
    private Integer quantity; //数量
    private Double price; //价格
    private Integer orderId; //订单表主键id
    private Integer state; // 状态:是否新增 0否，1是
    private Integer categoryId; //所属分类id
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
