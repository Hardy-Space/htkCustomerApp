package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/7/14.
 */

public class ShopStateDialog extends AlertDialog {

    Context context;

    public ShopStateDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rest);
    }
}
