package com.hl.htk_customer.service;

/**
 * Created by Administrator on 2017/10/25.
 *
 */

public interface OrderService {

    void cancelOrder(String orderNumber , int mark);

    void deleteOrder();

}
