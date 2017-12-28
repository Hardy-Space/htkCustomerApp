package com.hl.htk_customer.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.base_lib.dialog.BaseNiceDialog;
import com.example.base_lib.dialog.ViewHolder;
import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/10/20.
 * 外卖商品列表条目点击是显示商品具体信息的dialog
 */

public class ProductDetailDialog extends BaseNiceDialog {

    private String mData;

    public static ProductDetailDialog newInstance(String data) {

        Bundle args = new Bundle();
        args.putString("data" , data);

        ProductDetailDialog fragment = new ProductDetailDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mData = bundle.getString("data");
    }

    @Override
    public int iniLayoutId() {
        return R.layout.dialog_product_detail;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
//        holder.setText(R.id.product_title , );
    }
}
