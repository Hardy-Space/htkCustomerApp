package com.hl.htk_customer.hldc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.GvBean;

import java.util.List;


/**
 * Created by Administrator on 2017/11/1.
 */

public class GvAdapter extends BaseAdapter {
    private Context mContext;
    private List<GvBean> mList;

    public GvAdapter(Context context, List<GvBean> list) {
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
        final  ViewHolderGv viewHolderGv;
        if (view == null){
            viewHolderGv = new ViewHolderGv();
            view = LayoutInflater.from(mContext).inflate(R.layout.gv_layout,null);
            viewHolderGv.image = view.findViewById(R.id.img_tuxiang);
            viewHolderGv.name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(viewHolderGv);
        }else {
            viewHolderGv = (ViewHolderGv) view.getTag();
        }
            viewHolderGv.name.setText(mList.get(i).getName());
        return view;
    }

    static class ViewHolderGv{
        ImageView image;
        TextView name;
    }
}
