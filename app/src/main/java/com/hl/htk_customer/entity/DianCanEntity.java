package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/7/21.
 */

public class DianCanEntity {

    public boolean isChange = false;

    public DianCanEntity(boolean isChange) {
        this.isChange = isChange;
    }


    public boolean isChanged() {
        return isChange;
    }

}
