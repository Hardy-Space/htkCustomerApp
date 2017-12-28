package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CollectionDialog extends AlertDialog {

    public CollectionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_collection);
    }
}
