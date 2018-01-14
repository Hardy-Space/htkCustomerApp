package com.hl.htk_customer.adapter.wm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.assistant.ShopToDetailListener;
import com.hl.htk_customer.model.ShopProduct;

import java.util.List;

/**
 * Created by caobo on 2016/7/20.
 * 购物车适配器
 */
public class ShopAdapter extends BaseAdapter {

    private ShopToDetailListener shopToDetailListener;

    public void setShopToDetailListener(ShopToDetailListener callBackListener) {
        this.shopToDetailListener = callBackListener;
    }
    private List<ShopProduct> shopProducts;
    private LayoutInflater mInflater;
    private Context mContext;
    public ShopAdapter(Context context, List<ShopProduct> shopProducts) {
        mContext = context;
        this.shopProducts = shopProducts;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return shopProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return shopProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trade_widget, null);
            viewHolder = new ViewHolder();
            viewHolder.commodityName = (TextView) convertView.findViewById(R.id.commodityName);
            viewHolder.commodityPrise = (TextView) convertView.findViewById(R.id.commodityPrise);
            viewHolder.commodityNum = (TextView) convertView.findViewById(R.id.commodityNum);
            viewHolder.increase = (TextView)  convertView.findViewById(R.id.increase);
            viewHolder.reduce = (TextView)  convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView)  convertView.findViewById(R.id.shoppingNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commodityName.setText(shopProducts.get(position).getGoods());
        viewHolder.commodityPrise.setText(shopProducts.get(position).getPrice());
        viewHolder.commodityNum.setText(1+"");
        viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");

        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProducts.get(position).getNumber();
                if(num <= 0){
                    Toast.makeText(mContext,"该商品缺货状态，目前不可购买",Toast.LENGTH_SHORT).show();
                    return;
                }
                num++;
                shopProducts.get(position).setNumber(num);
                viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");
                if (shopToDetailListener != null) {
                    shopToDetailListener.onUpdateDetailList(shopProducts.get(position), "1");
                } else {
                }
            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProducts.get(position).getNumber();
                if (num > 0) {
                    num--;
                    if(num==0){
                        shopProducts.get(position).setNumber(num);
                        shopToDetailListener.onRemovePriduct(shopProducts.get(position));
                    }else {
                        shopProducts.get(position).setNumber(num);
                        viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");
                        if (shopToDetailListener != null) {
                            shopToDetailListener.onUpdateDetailList(shopProducts.get(position), "2");
                        } else {
                        }
                    }

                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        /**
         * 购物车商品名称
         */
        public TextView commodityName;
        /**
         * 购物车商品价格
         */
        public TextView commodityPrise;
        /**
         * 购物车商品数量
         */
        public TextView commodityNum;
        /**
         * 增加
         */
        public TextView increase;
        /**
         * 减少
         */
        public TextView reduce;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
    }
}
