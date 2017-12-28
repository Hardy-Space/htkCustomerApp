package com.hl.htk_customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TuanGouShopPhotoEntity;
import com.hl.htk_customer.utils.ImageLoadManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TuanGouShopPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private ArrayList<TuanGouShopPhotoEntity.DataBean> datas;//数据

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public TuanGouShopPhotoAdapter(Context context, ArrayList<TuanGouShopPhotoEntity.DataBean> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public int getItemViewType(int position) {
        //判断item类别，是图还是显示页数（图片有URL）
        if (!TextUtils.isEmpty(datas.get(position).getImgUrl())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //根据item类别加载不同ViewHolder
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tuangou_shop_photo, parent,
                    false);//这个布局就是一个imageview用来显示图片
            MyViewHolder holder = new MyViewHolder(view);
            //给布局设置点击和长点击监听
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if(holder instanceof MyViewHolder){
            ImageLoadManager.getInstance().setImage(datas.get(position).getImgUrl() ,
                    ((MyViewHolder) holder).iv_photo);//加载网络图片
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount()
    {
        return datas.size();//获取数据的个数
    }

    //点击事件回调
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int) v.getTag());
        }
    }
    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener!= null) {
            mOnItemClickListener.onItemLongClick(v,(int) v.getTag());
        }
        return false;
    }

    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv_photo;

        public MyViewHolder(View view)
        {
            super(view);
            iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
        }
    }

}
