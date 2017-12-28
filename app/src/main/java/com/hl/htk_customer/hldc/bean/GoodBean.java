package com.hl.htk_customer.hldc.bean;

import java.io.Serializable;


/**
 * Created by Administrator on 2017/10/26.
 */
public class GoodBean implements Serializable {
    private static final long serialVersionUID = 1309481009238123L;
    private int shopId;//商户id
    private int id; //产品id
    private double price; //产品价格
    private String  imgUrl; //产品图片
    private String productName; //产品名
    private int  grade; // 综合评分
    private int monthlySalesVolume; // 月售数量
    private int  collectState; //是否收藏  0未收藏，　１收藏
    private String categoryName; //商品分类名称
    private String productDetail; // 商品介绍
    private String description; // 商品描述
    private int quantity;
    private int categoryId;

    public GoodBean(int shopId, int id, double price, String imgUrl, String productName, int grade,
            int monthlySalesVolume, int isCollect, String productDetail, String description, int quantity,String categoryName) {
        this.shopId = shopId;
        this.id = id;
        this.price = price;
        this.imgUrl = imgUrl;
        this.productName = productName;
        this.grade = grade;
        this.monthlySalesVolume = monthlySalesVolume;
        this.collectState = isCollect;
        this.productDetail = productDetail;
        this.description = description;
        this.quantity = quantity;
        this.categoryName = categoryName;
    }

    public GoodBean() {
    }

    public GoodBean(int shopId, int id, double price, String imgUrl, String productName, int grade, int monthlySalesVolume,
            int collectState, String categoryName, String productDetail, String description, int quantity, int categoryId) {
        this.shopId = shopId;
        this.id = id;
        this.price = price;
        this.imgUrl = imgUrl;
        this.productName = productName;
        this.grade = grade;
        this.monthlySalesVolume = monthlySalesVolume;
        this.collectState = collectState;
        this.categoryName = categoryName;
        this.productDetail = productDetail;
        this.description = description;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getMonthlySalesVolume() {
        return monthlySalesVolume;
    }

    public void setMonthlySalesVolume(int monthlySalesVolume) {
        this.monthlySalesVolume = monthlySalesVolume;
    }

    public int getCollectState() {
        return collectState;
    }

    public void setCollectState(int isCollect) {
        this.collectState = isCollect;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "{productId:"+id+",productName:"+productName+",quantity:"+quantity+",price:"+price+"}" ;
    }
}
