package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/6/19.
 */

public class EventToHomeEntity {


    private boolean isHome = false;


    public EventToHomeEntity(boolean isHome) {
        this.isHome = isHome;
    }


    public boolean getIsHome() {

        return isHome;
    }


}
