package com.hl.htk_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.AddressListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 */

public class HomeAddressAdapter extends BaseAdapter {

    List<AddressListEntity.DataBean> list;
    Context context;
    LayoutInflater layoutInflater;
    private int tag = 0;  //0 不可修改  1 可修改


    public HomeAddressAdapter(Context context, int tag) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.tag = tag;
    }


    public void setData(List<AddressListEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public  List<AddressListEntity.DataBean>   getData(){
        return list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_home_address, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.bindData(tag, list, position);


        viewHolder.ivChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeListener.changeClick(position);
            }
        });

        return convertView;
    }


    public ChangeListener changeListener;

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ChangeListener {
        void changeClick(int position);
    }


    static class ViewHolder {
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.iv_change_address)
        ImageView ivChangeAddress;


        private void bindData(int tag, List<AddressListEntity.DataBean> list, int position) {

            if (tag == 0) {
                ivChangeAddress.setVisibility(View.GONE);
            } else {
                ivChangeAddress.setVisibility(View.VISIBLE);
            }

            AddressListEntity.DataBean dataBean = list.get(position);
            String sex = "";

            if (dataBean.getSex() == 0) {
                sex = "女士";
            } else {
                sex = "先生";
            }

            tvInfo.setText(dataBean.getUserName() + " " +sex +"   " + dataBean.getPhone());
            tvAddress.setText(dataBean.getLocation() + dataBean.getAddress());

        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
