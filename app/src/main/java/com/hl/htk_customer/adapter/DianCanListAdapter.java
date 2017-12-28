package com.hl.htk_customer.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/19.
 */

public class DianCanListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<DianCanFenLeiListEntity.DataBean> list;


    public DianCanListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<DianCanFenLeiListEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<DianCanFenLeiListEntity.DataBean> list) {
        this.list.addAll(list);
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
            convertView = layoutInflater.inflate(R.layout.item_diancan_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position, context);

        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chooseNum = list.get(position).getChooseNum();
                if (chooseNum == 0) {

                    chooseNum++;
                    list.get(position).setChooseNum(chooseNum);
                    viewHolder.tvChooseNum.setText(chooseNum + "");
                    viewHolder.tvChooseNum.setVisibility(View.VISIBLE);
                    viewHolder.tvRemove.setVisibility(View.VISIBLE);

                    int i = -(MyUtils.Dp2Px(context, 35));

                    ObjectAnimator animator0 = ObjectAnimator.ofFloat(viewHolder.tvChooseNum, "translationX", -70F);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(viewHolder.tvRemove, "translationX", -100F + i);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(500);
                    animatorSet.setInterpolator(new BounceInterpolator());
                    animatorSet.playTogether(animator0, animator1);
                    animatorSet.start();


                    // MyApplication.diancanList.add(list.get(position));
                    DianCanFenLeiListEntity.DataBean dataBean = list.get(position);
                    MyApplication.diancanList.add(new DianCanFenLeiListEntity.
                            DataBean(dataBean.getId(), dataBean.getProductName(), dataBean.getPrice(), dataBean.getImgUrl(), dataBean.getMonthlySalesVolume(), dataBean.getCategoryId(), dataBean.getChooseNum()));

                    EventBus.getDefault().post(new DianCanEntity(true));

                } else {
                    chooseNum++;
                    list.get(position).setChooseNum(chooseNum);
                    viewHolder.tvChooseNum.setText(chooseNum + "");

                    String productName = list.get(position).getProductName();
                    for (int i = 0; i < MyApplication.diancanList.size(); i++) {

                        List<DianCanFenLeiListEntity.DataBean> diancanList = MyApplication.diancanList;

                        if (diancanList.get(i).getProductName().equals(productName)) {
                            MyApplication.diancanList.get(i).setChooseNum(chooseNum);
                            break;
                        }

                    }

                    EventBus.getDefault().post(new DianCanEntity(true));

                }

            }
        });


        viewHolder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chooseNum = list.get(position).getChooseNum();

                if (chooseNum == 1) {
                    chooseNum--;
                    list.get(position).setChooseNum(chooseNum);
                    viewHolder.tvChooseNum.setText(chooseNum + "");
                    viewHolder.tvChooseNum.setVisibility(View.GONE);
                    viewHolder.tvRemove.setVisibility(View.GONE);

                    int i = MyUtils.Dp2Px(context, 35);

                    ObjectAnimator animator0 = ObjectAnimator.ofFloat(viewHolder.tvChooseNum, "translationX", 50F);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(viewHolder.tvRemove, "translationX", 100F + i);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(500);
                    animatorSet.setInterpolator(new BounceInterpolator());
                    animatorSet.playTogether(animator0, animator1);
                    animatorSet.start();

                    //    MyApplication.diancanList.remove(list.get(position));
                    for (int j = 0; j < MyApplication.diancanList.size(); j++) {
                        String productName = MyApplication.diancanList.get(j).getProductName();
                        if (productName.equals(list.get(position).getProductName())) {
                            MyApplication.diancanList.remove(j);
                            break;
                        }
                    }

                    EventBus.getDefault().post(new DianCanEntity(true));
                } else {
                    chooseNum--;
                    list.get(position).setChooseNum(chooseNum);
                    viewHolder.tvChooseNum.setText(chooseNum + "");


                    for (int i = 0; i < MyApplication.diancanList.size(); i++) {

                        String productName = MyApplication.diancanList.get(i).getProductName();
                        if (productName.equals(list.get(position).getProductName())) {
                            MyApplication.diancanList.get(i).setChooseNum(chooseNum);
                            break;
                        }

                    }

                    EventBus.getDefault().post(new DianCanEntity(true));
                }


            }
        });


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_remove)
        TextView tvRemove;
        @Bind(R.id.tv_add)
        TextView tvAdd;
        @Bind(R.id.tv_choose_num)
        TextView tvChooseNum;


        private void bindData(List<DianCanFenLeiListEntity.DataBean> list, int position, Context context) {
            DianCanFenLeiListEntity.DataBean dataBean = list.get(position);
            try {
                image.setImageURI(Uri.parse(dataBean.getImgUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvName.setText(dataBean.getProductName());
            tvNum.setText("月售" + dataBean.getMonthlySalesVolume() + "单");
            tvPrice.setText(dataBean.getPrice() + "元");
            tvChooseNum.setText(dataBean.getChooseNum() + "");

            if (dataBean.getChooseNum() == 0) {
                tvRemove.setVisibility(View.GONE);
                tvChooseNum.setVisibility(View.GONE);
            } else {


                tvChooseNum.setVisibility(View.VISIBLE);
                tvRemove.setVisibility(View.VISIBLE);

                int i = -(MyUtils.Dp2Px(context, 35));

                ObjectAnimator animator0 = ObjectAnimator.ofFloat(tvChooseNum, "translationX", -70F);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(tvRemove, "translationX", -100F + i);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                animatorSet.setInterpolator(new BounceInterpolator());
                animatorSet.playTogether(animator0, animator1);
                animatorSet.start();


            }

        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
