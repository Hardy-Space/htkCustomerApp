package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class YiDianAdapter extends BaseAdapter {

    Context context;
    List<DianCanFenLeiListEntity.DataBean> list;
    LayoutInflater layoutInflater;

    public YiDianAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<DianCanFenLeiListEntity.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_yidian, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position);

        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = list.get(position).getChooseNum();
                num++;
                viewHolder.tvNum.setText(num + "");

                list.get(position).setChooseNum(num);
                notifyDataSetChanged();
                EventBus.getDefault().post(new DianCanEntity(true));

            }
        });

        viewHolder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = list.get(position).getChooseNum();
                num--;
                if (num == 0) {
                    list.remove(position);
                } else {
                    list.get(position).setChooseNum(num);
                }
                notifyDataSetChanged();
                EventBus.getDefault().post(new DianCanEntity(true));

            }
        });


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_remove)
        TextView tvRemove;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_add)
        TextView tvAdd;


        private void bindData(List<DianCanFenLeiListEntity.DataBean> list, int position) {

            DianCanFenLeiListEntity.DataBean dataBean = list.get(position);
            image.setImageURI(Uri.parse(dataBean.getImgUrl()));
            tvName.setText(dataBean.getProductName());
            tvPrice.setText(dataBean.getPrice() + "元");
            tvNum.setText(dataBean.getChooseNum() + "");

        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
