package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hl.htk_customer.R;


/**
 * 作者 龙威陶
 * 说明
 * 创建时间 2017/1/22
 * 公司名称
 * 描述
 */
public class CallDialog extends AlertDialog {

    Context context;
    String phoneNumber = "";
    String content = "";

    public CallDialog(Context context, String phoneNumber, String content) {
        super(context);
        this.context = context;
        this.phoneNumber = phoneNumber;
        this.content = content;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lianxi);
        final TextView phone = (TextView) findViewById(R.id.text_phone);
        TextView lianxi = (TextView) findViewById(R.id.text_lianxi);
        phone.setText(phoneNumber);
        lianxi.setText(content);
        lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone.getText().toString().trim()));
                context.startActivity(intent);
            }
        });
    }
}
