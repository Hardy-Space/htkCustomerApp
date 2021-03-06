package com.hl.htk_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.model.ActionModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ActionAdapter extends BaseAdapter {
    List<ActionModel> list;
    LayoutInflater layoutInflater;
    Context context;


    public ActionAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<ActionModel> list) {

        this.list = list;
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_action, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;

        private void bindData(List<ActionModel> list, int position) {

            tvContent.setText(list.get(position).getContent());

        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
