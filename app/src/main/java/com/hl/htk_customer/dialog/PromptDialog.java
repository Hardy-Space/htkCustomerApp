package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/9/16.
 */

public class PromptDialog implements View.OnClickListener {

    private final LayoutInflater mInflater;
    private final AlertDialog.Builder builder;

    private AlertDialog dialog;
    private DialogOnClickListener listener;

    public PromptDialog(Context context){
        mInflater = LayoutInflater.from(context);
        builder = new AlertDialog.Builder(context);
    }

    public static PromptDialog builder(Context context){
        return new PromptDialog(context);
    }

    public void setListener(DialogOnClickListener listener) {
        this.listener = listener;
    }

    public PromptDialog create(String content){
        View view = mInflater.inflate(R.layout.dialog_prompt, null , false);
        TextView positive = (TextView) view.findViewById(R.id.dialog_prompt_positive);
        TextView negative = (TextView) view.findViewById(R.id.dialog_prompt_negative);
        TextView tvContent = (TextView) view.findViewById(R.id.dialog_prompt_content);

        tvContent.setText(content);

        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        dialog.getWindow().setContentView(view);

        return this;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_prompt_positive:
                listener.onPositive();
                dialog.dismiss();
                break;
            case R.id.dialog_prompt_negative:
                listener.onNegative();
                dialog.dismiss();
                break;
        }
    }
}
