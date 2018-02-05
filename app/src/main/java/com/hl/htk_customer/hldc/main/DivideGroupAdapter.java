package com.hl.htk_customer.hldc.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.model.AlreadySelectFoodData;

import java.util.List;


/**
 * Created by asus on 2017/10/30.
 */

public class DivideGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<OrderFoodBean> mList;

    public DivideGroupAdapter(Context context, List<OrderFoodBean> arrayList){
        mContext = context;
        mList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
//        switch (viewType){
//            case TYPE_HEAD:
//                ViewGroup headView = (ViewGroup)inflater.inflate(R.layout.dividegroup_head_layout,parent,false);
//                ViewHeadView mView = new ViewHeadView(headView);
//                return mView;
//            case TYPE_CONTENT:
//                ViewGroup contentView = (ViewGroup)inflater.inflate(R.layout.dividegroup_content_layout,parent,false);
//                ViewContentView mView1 = new ViewContentView(contentView);
//                return mView1;
//        }
        ViewGroup contentView = (ViewGroup)inflater.inflate(R.layout.dividegroup_content_layout,parent,false);
        ViewContentView mView1 = new ViewContentView(contentView);
        return mView1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewContentView vhImage = (ViewContentView) holder;
        vhImage.tvFoodName.setText(mList.get(position).getProductName());
        vhImage.tvFoodPrice.setText(mList.get(position).getPrice()+"");
        vhImage.tvNumber.setText(mList.get(position).getQuantity()+"");
        Glide.with(mContext).load(mList.get(position).getImgUrl()).into(vhImage.imgIcon);
//        vhImage.imgIcon.setBackgroundResource();
        if(position == 0){
            vhImage.linearContent.setVisibility(View.VISIBLE);
            vhImage.tvHeadView.setText(mList.get(position).getCategoryName()+"");
        }else{
            if(mList.get(position).getCategoryId() == mList.get(position-1).getCategoryId()){
                vhImage.linearContent.setVisibility(View.GONE);
            } else {
                vhImage.linearContent.setVisibility(View.VISIBLE);
            }
            vhImage.tvHeadView.setText(mList.get(position).getCategoryName()+"");
        }
        vhImage.tvPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int newQuantity = mList.get(position).getQuantity()+1;
                mList.get(position).setQuantity(newQuantity);

                AlreadySelectFoodData.getAllFoodList().get(position).setQuantity(newQuantity);

//                notifyItemChanged(position, "aa");
                OrderedListActivity.mInstance.showModify();
            }
        });
        vhImage.tvReduce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mList.get(position).getQuantity()<=0)
                    return;
                else {
                    int newQuantity =mList.get(position).getQuantity()-1;
                    mList.get(position).setQuantity(newQuantity);
                    AlreadySelectFoodData.getAllFoodList().get(position).setQuantity(newQuantity);
                }
//                notifyItemChanged(position, "aa");
                OrderedListActivity.mInstance.showModify();
            }
        });


        vhImage.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除菜品
                mList.remove(position);
                AlreadySelectFoodData.getAllFoodList().remove(position);
                OrderedListActivity.mInstance.showModify();
            }
        });

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder, position);
        } else {
            String payload = (String)payloads.get(0);
            OrderFoodBean mBean = mList.get(position);
            ViewContentView contentView = (ViewContentView) holder;
            contentView.tvNumber.setText(mBean.getQuantity()+"");
        }
//        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewContentView extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView imgIcon;
        public TextView tvFoodPrice;
        public TextView tvFoodName;
        public TextView tvPlus;
        public TextView tvReduce;
        public TextView tvNumber;
        public TextView tvHeadView;
        public TextView tvDelete;
        public LinearLayout linearContent;
        public ViewContentView(View mView){
            super(mView);
            rootView = mView;
            tvHeadView = rootView.findViewById(R.id.tv_grouphead);
            imgIcon = rootView.findViewById(R.id.img_foodicon);
            tvFoodName = rootView.findViewById(R.id.tv_foodname);
            tvFoodPrice = rootView.findViewById(R.id.tv_foodprice);
            tvPlus = rootView.findViewById(R.id.tv_plus);
            tvReduce = rootView.findViewById(R.id.tv_reduce);
            tvNumber = rootView.findViewById(R.id.tv_number1);
            linearContent = rootView.findViewById(R.id.linear_head);

            tvDelete = rootView.findViewById(R.id.tv_delete);
        }
    }
}
