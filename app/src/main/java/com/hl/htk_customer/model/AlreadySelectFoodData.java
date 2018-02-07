package com.hl.htk_customer.model;

import com.hl.htk_customer.hldc.bean.OrderFoodBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马鹏昊
 * @date {2018-2-5}
 * @des 保存本次点餐已点商品信息
 * @updateAuthor
 * @updateDate
 * @updateDes
 */

public class AlreadySelectFoodData {

    private static String shopId;

    public static String getShopId() {
        return shopId;
    }

    public static void setShopId(String shopId) {
        AlreadySelectFoodData.shopId = shopId;
    }

    private static final List<OrderFoodBean> allFood = new ArrayList<>();

    public static List<OrderFoodBean> getAllFoodList() {
        return allFood;
    }

    public static void addBean(OrderFoodBean bean) {
        for (int i = 0; i < allFood.size(); i++) {
            OrderFoodBean existBean = allFood.get(i);
            if (existBean.getCategoryId() == bean.getCategoryId()) {
                allFood.add(i, bean);
                return;
            }
        }
        allFood.add(bean);
        return;
    }

}
