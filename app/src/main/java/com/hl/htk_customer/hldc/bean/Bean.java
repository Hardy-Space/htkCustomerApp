package com.hl.htk_customer.hldc.bean;

import java.util.ArrayList;

/**
 * Created by asus on 2017/10/30.
 */

public class Bean {

    public String foodType;
    public ArrayList<FoodBean> mList = new ArrayList<>();

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public ArrayList<FoodBean> getmList() {
        return mList;
    }

    public void setmList(ArrayList<FoodBean> mList) {
        this.mList = mList;
    }
}
