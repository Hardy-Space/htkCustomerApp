package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/6/30.
 */

public class MarkDialog extends AlertDialog {


    public MarkDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mark);
    }
}
