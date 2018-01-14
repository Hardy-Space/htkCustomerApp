package com.hl.htk_customer.adapter.wm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.assistant.onCallBackListener;
import com.hl.htk_customer.model.ProductType;
import com.hl.htk_customer.model.ShopProduct;

import java.util.List;

public class TestSectionedAdapter extends SectionedBaseAdapter {

    List<ProductType> pruductCagests;
    private HolderClickListener mHolderClickListener;
    private Context context;
    private LayoutInflater mInflater;


    private onCallBackListener callBackListener;

    public void setCallBackListener(onCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }


    public TestSectionedAdapter(Context context, List<ProductType> pruductCagests) {
        this.context = context;
        this.pruductCagests = pruductCagests;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int section, int position) {
        return pruductCagests.get(section).getProduct().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return pruductCagests.size();
    }

    @Override
    public int getCountForSection(int section) {
        return pruductCagests.get(section).getProduct().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_item, null);
            viewHolder = new ViewHolder();
            viewHolder.head = (SimpleDraweeView) convertView.findViewById(R.id.head);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.prise = (TextView) convertView.findViewById(R.id.prise);
            viewHolder.increase = (TextView) convertView.findViewById(R.id.increase);
            viewHolder.reduce = (TextView) convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView) convertView.findViewById(R.id.shoppingNum);
            viewHolder.tv_kucun = (TextView) convertView.findViewById(R.id.tv_inventory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShopProduct product = pruductCagests.get(section).getProduct().get(position);
        viewHolder.name.setText(product.getGoods());
        viewHolder.prise.setText(String.format(context.getString(R.string.join_order_money) , Double.valueOf(product.getPrice())));
        viewHolder.shoppingNum.setText(String.valueOf(product.getNumber()));
        viewHolder.head.setImageURI(Uri.parse(product.getPicture()));
        viewHolder.tv_kucun.setText(String.format(context.getString(R.string.join_inventory) , String.valueOf(product.getInventory())));
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = product.getNumber();
                int inventory = product.getInventory();
                if(inventory <= 0){   // 根据库存来判断是否可以增加订单商品数量
                    Toast.makeText(context,"该商品缺货状态，目前不可购买",Toast.LENGTH_SHORT).show();
                    return;
                }
                num++;
                product.setNumber(num);
                viewHolder.shoppingNum.setText(product.getNumber() + "");
                if (callBackListener != null) {
                    callBackListener.updateProduct(product, "1");
                } else {
                }
                if (mHolderClickListener != null) {
                    int[] start_location = new int[2];
                    viewHolder.shoppingNum.getLocationInWindow(start_location);//获取点击商品图片的位置
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.adddetail);//复制一个新的商品图标
                    //TODO:解决方案，先监听到左边ListView的Item中，然后在开始动画添加
                    mHolderClickListener.onHolderClick(drawable, start_location);
                }
            }
        });
        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = product.getNumber();
                if (num > 0) {
                    num--;
                    product.setNumber(num);
                    viewHolder.shoppingNum.setText(product.getNumber() + "");
                    if (callBackListener != null) {
                        callBackListener.updateProduct(product, "2");
                    } else {
                    }
                }
            }
        });

        viewHolder.shoppingNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    int shoppingNum = Integer.parseInt(viewHolder.shoppingNum.getText().toString());
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        /**
         * 商品图片
         */
        public SimpleDraweeView head;
        /**
         * 商品名称
         */
        public TextView name;
        /**
         * 商品价格
         */
        public TextView prise;
        /**
         * 增加
         */
        public TextView increase;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
        /**
         * 减少
         */
        public TextView reduce;
        /*
        * 库存
        * */
        public TextView tv_kucun;

    }

    public void SetOnSetHolderClickListener(HolderClickListener holderClickListener) {
        this.mHolderClickListener = holderClickListener;
    }

    public interface HolderClickListener {
        public void onHolderClick(Drawable drawable, int[] start_location);
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(pruductCagests.get(section).getType());
        return layout;
    }

}
