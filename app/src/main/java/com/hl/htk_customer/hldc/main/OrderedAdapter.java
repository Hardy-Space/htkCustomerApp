package com.hl.htk_customer.hldc.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;

import java.util.List;


/**
 * Created by asus on 2017/10/27.--common used recyclerview adapter
 */

public class OrderedAdapter extends RecyclerView.Adapter<OrderedAdapter.MyViewHolder> {
    Context mContext;
    List<OrderFoodBean> mList;
    public OrderedAdapter(Context context, List<OrderFoodBean> arrayList){
        mContext = context;
        mList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.comfirm_order_foodlist_item, parent, false));
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvFoodName.setText(mList.get(position).getProductName());
        holder.tvFoodPrice.setText(mList.get(position).getPrice()+mContext.getResources().getString(R.string.yuan1));
        holder.tvFoodMount.setText(mList.get(position).getQuantity()+"");
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFoodName, tvFoodMount, tvFoodPrice;
        public MyViewHolder(View view){
            super(view);
            tvFoodName = view.findViewById(R.id.tv_foodname);
            tvFoodMount = view.findViewById(R.id.tv_foodamount);
            tvFoodPrice = view.findViewById(R.id.tv_foodprice);
        }
    }

}
