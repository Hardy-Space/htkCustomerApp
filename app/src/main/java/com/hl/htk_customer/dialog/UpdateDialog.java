package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.hl.htk_customer.R;


/**
 * 作者 龙威陶
 * 说明
 * 创建时间 2017/3/23
 * 公司名称 百迅科技
 * 描述
 */
public class UpdateDialog extends AlertDialog {

    Context context;

    public UpdateDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
    }
}
