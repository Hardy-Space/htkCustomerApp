package com.hl.htk_customer.hldc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.TcBean;

import java.util.List;


/**
 * Created by Administrator on 2017/10/27.
 */

public class TcAdapter extends BaseAdapter {
    private Context mContext;
    private List<TcBean> mList;

    public TcAdapter(Context context, List<TcBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolderTC mviewHolder;
        if (view == null){
            mviewHolder = new ViewHolderTC();
            view = LayoutInflater.from(mContext).inflate(R.layout.tclv_item_layout,null);
            mviewHolder.img = view.findViewById(R.id.tc_img);
            mviewHolder.name = view.findViewById(R.id.tc_name);
            mviewHolder.num = view.findViewById(R.id.tc_num);
            mviewHolder.price = view.findViewById(R.id.tc_price);
            view.setTag(mviewHolder);
        }else {
            mviewHolder = (ViewHolderTC) view.getTag();
        }
        Glide.with(mContext).load(mList.get(i).getImgurl()).into(mviewHolder.img);
        mviewHolder.name.setText(mList.get(i).getName());
        mviewHolder.num.setText(mList.get(i).getNum()+"");
        mviewHolder.price.setText(mList.get(i).getPrice()+mContext.getResources().getString(R.string.yuan1));
        return view;
    }

    static class ViewHolderTC{
        ImageView img;
        TextView name,num,price;
    }
}
