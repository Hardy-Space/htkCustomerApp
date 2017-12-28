package com.hl.htk_customer.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by caobo on 2016/7/12 0012.
 * 小区商圈商品属性
 */
public class ShopProduct implements Parcelable {
    private int id;
    private String shopName;//店名
    private String price;//单价
    private String goods;//货物名称
    private String picture;//货物图片
    private String type;//货物类型
    private String createtime;

    /**
     * 商品数目
     */
    private int number;

    private String money;

    private int inventory;//库存


    public ShopProduct(String goods, int number, String price, int id) {

        this.goods = goods;
        this.price = price;
        this.number = number;
        this.id = id;

    }


    public ShopProduct() {

    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(shopName);
        dest.writeString(price);
        dest.writeString(goods);
        dest.writeString(picture);
        dest.writeString(type);
        dest.writeString(createtime);
        dest.writeInt(number);
        dest.writeString(money);
    }


    public static final Parcelable.Creator<ShopProduct> CREATOR = new Creator<ShopProduct>() {
        public ShopProduct createFromParcel(Parcel source) {
            ShopProduct shopProduct = new ShopProduct();
            shopProduct.id = source.readInt();
            shopProduct.shopName = source.readString();
            shopProduct.price = source.readString();
            shopProduct.goods = source.readString();
            shopProduct.picture = source.readString();
            shopProduct.type = source.readString();
            shopProduct.createtime = source.readString();
            shopProduct.number = source.readInt();
            shopProduct.money = source.readString();

            return shopProduct;
        }

        public ShopProduct[] newArray(int size) {
            return new ShopProduct[size];
        }
    };


}
