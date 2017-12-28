package com.hl.htk_customer.model;

import android.widget.CheckBox;

/**
 * Created by Administrator on 2017/10/31.
 *
 */

public class CheckChangeEvent {
    private boolean check;
    private CheckBox checkBox;

    public CheckChangeEvent(CheckBox checkBox ,boolean check) {
        this.check = check;
        this.checkBox = checkBox;
    }

    public boolean isCheck() {
        return check;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}
