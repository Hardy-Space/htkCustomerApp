package com.hl.htk_customer.hldc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.main.DCMainActivity;
import com.hl.htk_customer.hldc.main.GoodDetailActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Administrator on 2017/10/26.
 */

public class LvAdapter extends BaseAdapter {
    private static final String TAG = LvAdapter.class.getSimpleName();
    private Context mContext;
    private List<GoodBean> mList;
    public LvAdapter(Context context, List<GoodBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder mviewHolder;
        if (view == null){
            mviewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.lv_item_layout,null);
            mviewHolder.img_add = view.findViewById(R.id.img_add);
            mviewHolder.lv_image = view.findViewById(R.id.lv_image);
            mviewHolder.img_shoucang = view.findViewById(R.id.img_shoucang);
            mviewHolder.xxa = view.findViewById(R.id.xingxinga);
            mviewHolder.xxb = view.findViewById(R.id.xingxingb);
            mviewHolder.xxc = view.findViewById(R.id.xingxingc);
            mviewHolder.xxd = view.findViewById(R.id.xingxingd);
            mviewHolder.xxe = view.findViewById(R.id.xingxinge);
            mviewHolder.lv_item_title = view.findViewById(R.id.lv_item_title);
            mviewHolder.tv_price = view.findViewById(R.id.tv_price);
            mviewHolder.tv_yuexiao = view.findViewById(R.id.tv_yuexiao);
            mviewHolder.lin = view.findViewById(R.id.lin);
            mviewHolder.tv_shoucang = view.findViewById(R.id.shoucang);
            view.setTag(mviewHolder);
        }else {
            mviewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(mList.get(i).getImgUrl()).into(mviewHolder.lv_image);
        mviewHolder.tv_price.setText(""+mList.get(i).getPrice());
        mviewHolder.tv_yuexiao.setText(mList.get(i).getMonthlySalesVolume()+"");
        mviewHolder.lv_item_title.setText(mList.get(i).getProductName());
        if(mList.get(i).getCollectState() == 1){
            mviewHolder.img_shoucang.setBackgroundResource(R.drawable.icon_heart);
        }else{
            mviewHolder.img_shoucang.setBackgroundResource(R.drawable.icon_heart_grey);
        }
        if (mList.get(i).getGrade() == 0){
            mviewHolder.xxa.setImageResource(R.drawable.huixing);
            mviewHolder.xxb.setImageResource(R.drawable.huixing);
            mviewHolder.xxc.setImageResource(R.drawable.huixing);
            mviewHolder.xxd.setImageResource(R.drawable.huixing);
            mviewHolder.xxe.setImageResource(R.drawable.huixing);
        }else if (mList.get(i).getGrade() == 1){
            mviewHolder.xxa.setImageResource(R.drawable.hongxing);
            mviewHolder.xxb.setImageResource(R.drawable.huixing);
            mviewHolder.xxc.setImageResource(R.drawable.huixing);
            mviewHolder.xxd.setImageResource(R.drawable.huixing);
            mviewHolder.xxe.setImageResource(R.drawable.huixing);
        }else if (mList.get(i).getGrade() == 2){
            mviewHolder.xxa.setImageResource(R.drawable.hongxing);
            mviewHolder.xxb.setImageResource(R.drawable.hongxing);
            mviewHolder.xxc.setImageResource(R.drawable.huixing);
            mviewHolder.xxd.setImageResource(R.drawable.huixing);
            mviewHolder.xxe.setImageResource(R.drawable.huixing);
        }else if (mList.get(i).getGrade() == 3){
            mviewHolder.xxa.setImageResource(R.drawable.hongxing);
            mviewHolder.xxb.setImageResource(R.drawable.hongxing);
            mviewHolder.xxc.setImageResource(R.drawable.hongxing);
            mviewHolder.xxd.setImageResource(R.drawable.huixing);
            mviewHolder.xxe.setImageResource(R.drawable.huixing);
        }else if (mList.get(i).getGrade() == 4){
            mviewHolder.xxa.setImageResource(R.drawable.hongxing);
            mviewHolder.xxb.setImageResource(R.drawable.hongxing);
            mviewHolder.xxc.setImageResource(R.drawable.hongxing);
            mviewHolder.xxd.setImageResource(R.drawable.hongxing);
            mviewHolder.xxe.setImageResource(R.drawable.huixing);
        }else if (mList.get(i).getGrade() == 5){
            mviewHolder.xxa.setImageResource(R.drawable.hongxing);
            mviewHolder.xxb.setImageResource(R.drawable.hongxing);
            mviewHolder.xxc.setImageResource(R.drawable.hongxing);
            mviewHolder.xxd.setImageResource(R.drawable.hongxing);
            mviewHolder.xxe.setImageResource(R.drawable.hongxing);
        }

        mviewHolder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"快捷点菜",Toast.LENGTH_SHORT).show();
                DCMainActivity.mainActivity.addFoodToList(mList.get(i));
            }
        });

        mviewHolder.img_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mList.get(i).getCollectState() == 1){
                    postIsCollect(i, 0);
                }else {
                    postIsCollect(i, 1);
                }
            }
        });
        mviewHolder.tv_shoucang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mList.get(i).getCollectState() == 1){
                    postIsCollect(i, 0);
                }else {
                    postIsCollect(i, 1);
                }
            }
        });
        mviewHolder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,GoodDetailActivity.class);
                intent.putExtra("goodsdetail",mList.get(i));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private void addToBuyCar(final int position){
        Log.d(TAG,"addToBuyCar() == isCollect == >>"+mList.get(position).getCollectState()+"_id=>");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MyApplication.dbUtils.insertGoodBean(mList.get(position));
//            }
//        }, 100);
    }

    static class ViewHolder{
        ImageView lv_image,img_shoucang,img_add,xxa,xxb,xxc,xxd,xxe;
        TextView lv_item_title,tv_yuexiao,tv_price, tv_shoucang;
        LinearLayout lin;
    }

    private void postIsCollect(final int position, int isCollect){
        HttpHelper.getInstance().setIsCollect(mContext, mList.get(position).getId(), isCollect, new JsonHandler<String>(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString, Object response) {
                Log.d(TAG, "onSuccess == >>>"+responseString);
                JSONObject object;
                int state = 0;
                try {
                    object = new JSONObject(responseString);
                    state = object.optInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(state == 100 && mList.get(position).getCollectState() == 1){
                    mList.get(position).setCollectState(0);
                    notifyDataSetChanged();
                    Toast.makeText(mContext,"已取消收藏",Toast.LENGTH_SHORT).show();
                }else if(state == 100 && mList.get(position).getCollectState() == 0){
                    mList.get(position).setCollectState(1);
                    notifyDataSetChanged();
                    Toast.makeText(mContext,"已收藏",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                Log.d(TAG, "onFailure == >>>"+responseString);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }
}
