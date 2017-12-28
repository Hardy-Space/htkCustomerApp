package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TimeChangeEvent {

    private String time;
    private boolean cancle;

    public TimeChangeEvent(String time , boolean cancle) {
        this.time = time;
        this.cancle = cancle;
    }

    public String getTime() {
        return time;
    }

    public boolean isCancle() {
        return cancle;
    }
}
