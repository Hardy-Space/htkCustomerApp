package com.hl.htk_customer.hldc.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.adapter.CommentAdapter;
import com.hl.htk_customer.hldc.adapter.TcAdapter;
import com.hl.htk_customer.hldc.bean.CommentBean;
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.bean.TcBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.hldc.view.MyListview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 2017/10/25.--- 商品详情页
 */

public class GoodDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = GoodDetailActivity.class.getSimpleName();
    private ImageView img_zhutu,xxa,xxb,xxc,xxd,xxe,img_shoucang,img_jia,img_jian,img_lefticon;
    private TextView tv_title,tv_price,tv_yx,tv_sum,tv_pinglunshu,taocanprice,zongjia,tv_jianjie,tv_leftstate,tv_common_title;
    private TextView tvFoodAmount, tvTotalPrice, tvComfirmOrder;
    private MyListview spxq_listview,spxq_pinglun;
    private LinearLayout linearTc;
    private View btmView;
    private int num=1;
    private TcAdapter mTcAdapter;
    private List<TcBean> mList = new ArrayList<>();
    private List<CommentBean> myList = new ArrayList<>();
    private Intent mIntent;
    private GoodBean mGoodBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = this.getIntent();
        mGoodBean = (GoodBean) mIntent.getSerializableExtra("goodsdetail");
        Log.d(TAG,""+mGoodBean.getProductDetail()+""+mGoodBean.getProductName()+""+mGoodBean.getId()+""+mGoodBean.getCollectState());
//        getSupportActionBar().hide();
        setContentView(R.layout.spxq_layout);
        initView();
        refreshUI();
        setStarNumber();
        getGoodsCommentsList();
        getTcList();
    }

    private void initdata() {
        if (mTcAdapter == null){
            mTcAdapter = new TcAdapter(this,mList);
            spxq_listview.setAdapter(mTcAdapter);
        }else {
            mTcAdapter.notifyDataSetChanged();
        }
    }

    private void isShowTaoCan(boolean isShow){
        if(isShow){
            spxq_listview.setVisibility(View.VISIBLE);
            linearTc.setVisibility(View.VISIBLE);
            initdata();
        }else{
            spxq_listview.setVisibility(View.GONE);
            linearTc.setVisibility(View.GONE);
        }
    }

    private void initCommentsList(){
        if(myList != null && myList.size() > 0){
            tv_pinglunshu.setText(getResources().getString(R.string.goodscommentstip)+"("+myList.size()+")");
            spxq_pinglun.setAdapter(new CommentAdapter(GoodDetailActivity.this, myList));
        } else {
            tv_pinglunshu.setVisibility(View.GONE);
            spxq_pinglun.setVisibility(View.GONE);
        }

    }

    private void refreshUI(){
        Glide.with(this).load(mGoodBean.getImgUrl()).into(img_zhutu);
        tv_common_title.setText(""+mGoodBean.getProductName());
        tv_price.setText(""+mGoodBean.getPrice()+getResources().getString(R.string.yuan1));
        taocanprice.setText(getResources().getString(R.string.taocanjia)+mGoodBean.getPrice());
        if(mGoodBean.getCollectState() == 1){
            img_shoucang.setBackgroundResource(R.drawable.icon_heart);
        } else {
            img_shoucang.setBackgroundResource(R.drawable.icon_heart_grey);
        }
        tv_yx.setText(getResources().getString(R.string.monthsale)+""+mGoodBean.getMonthlySalesVolume()
                +getResources().getString(R.string.danwei));
        updateTotalPriceAndNumber(1);
    }

    // 点击加减的时候更新UI
    private void updateTotalPriceAndNumber(int amount){
        tvFoodAmount.setText(""+amount);
        tvTotalPrice.setText(""+mGoodBean.getPrice()*amount);
        zongjia.setText(getResources().getString(R.string.zongjia)+mGoodBean.getPrice()*amount);
        mGoodBean.setQuantity(num);
    }

    private void setStarNumber(){
        if(mGoodBean.getGrade() == 0){
            xxa.setBackgroundResource(R.drawable.huixing);
            xxb.setBackgroundResource(R.drawable.huixing);
            xxc.setBackgroundResource(R.drawable.huixing);
            xxd.setBackgroundResource(R.drawable.huixing);
            xxe.setBackgroundResource(R.drawable.huixing);
        }else if(mGoodBean.getGrade() == 1){
            xxa.setBackgroundResource(R.drawable.hongxing);
            xxb.setBackgroundResource(R.drawable.huixing);
            xxc.setBackgroundResource(R.drawable.huixing);
            xxd.setBackgroundResource(R.drawable.huixing);
            xxe.setBackgroundResource(R.drawable.huixing);
        }else if(mGoodBean.getGrade() == 2){
            xxa.setBackgroundResource(R.drawable.hongxing);
            xxb.setBackgroundResource(R.drawable.hongxing);
            xxc.setBackgroundResource(R.drawable.huixing);
            xxd.setBackgroundResource(R.drawable.huixing);
            xxe.setBackgroundResource(R.drawable.huixing);
        }else if(mGoodBean.getGrade() == 3){
            xxa.setBackgroundResource(R.drawable.hongxing);
            xxb.setBackgroundResource(R.drawable.hongxing);
            xxc.setBackgroundResource(R.drawable.hongxing);
            xxd.setBackgroundResource(R.drawable.huixing);
            xxe.setBackgroundResource(R.drawable.huixing);
        }else if(mGoodBean.getGrade() == 4){
            xxa.setBackgroundResource(R.drawable.hongxing);
            xxb.setBackgroundResource(R.drawable.hongxing);
            xxc.setBackgroundResource(R.drawable.hongxing);
            xxd.setBackgroundResource(R.drawable.hongxing);
            xxe.setBackgroundResource(R.drawable.huixing);
        }else if(mGoodBean.getGrade() == 5){
            xxa.setBackgroundResource(R.drawable.hongxing);
            xxb.setBackgroundResource(R.drawable.hongxing);
            xxc.setBackgroundResource(R.drawable.hongxing);
            xxd.setBackgroundResource(R.drawable.hongxing);
            xxe.setBackgroundResource(R.drawable.hongxing);
        }
    }

    private void initView() {
        img_lefticon = (ImageView) findViewById(R.id.img_lefticon);
        img_zhutu = (ImageView) findViewById(R.id.img_zhutu);
        xxa = (ImageView) findViewById(R.id.xingxinga);
        xxb = (ImageView) findViewById(R.id.xingxingb);
        xxc = (ImageView) findViewById(R.id.xingxingc);
        xxd = (ImageView) findViewById(R.id.xingxingd);
        xxe = (ImageView) findViewById(R.id.xingxinge);
        tv_yx = (TextView) findViewById(R.id.tv_yuexiao);
        img_shoucang = (ImageView) findViewById(R.id.img_shoucang);
        img_jia = (ImageView) findViewById(R.id.img_add);
        img_jian = (ImageView) findViewById(R.id.img_jian);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_sum = (TextView) findViewById(R.id.tv_sum);
        tv_pinglunshu = (TextView) findViewById(R.id.tv_pinglunshu);
        taocanprice = (TextView) findViewById(R.id.taocanprice);
        linearTc = (LinearLayout) findViewById(R.id.linear_tc);
        zongjia = (TextView) findViewById(R.id.zongjia);
        tv_jianjie = (TextView) findViewById(R.id.tv_jianjie);
        tv_leftstate = (TextView) findViewById(R.id.tv_leftstate);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        spxq_listview = (MyListview) findViewById(R.id.spxq_listview);
        spxq_pinglun = (MyListview) findViewById(R.id.spxq_pinglun);
        tv_leftstate.setText(""+getResources().getString(R.string.goback));
        img_lefticon.setImageResource(R.drawable.icon_goback);
        btmView = findViewById(R.id.bbbb);
        tvFoodAmount = btmView.findViewById(R.id.tv_number);
        tvTotalPrice = btmView.findViewById(R.id.tv_moneytip);
        tvComfirmOrder = btmView.findViewById(R.id.tv_bottompay);
        tvComfirmOrder.setText(getResources().getString(R.string.addtoorderlist));
        tvComfirmOrder.setOnClickListener(this);
        img_lefticon.setOnClickListener(this);
        tv_leftstate.setOnClickListener(this);
        img_shoucang.setOnClickListener(this);
        img_jia.setOnClickListener(this);
        img_jian.setOnClickListener(this);
        tv_sum.setText(num+"");
        tv_jianjie.setText(mGoodBean.getProductDetail()+"");
        mGoodBean.setQuantity(num);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_lefticon:
            case R.id.tv_leftstate:
                finish();
                break;
            case R.id.img_shoucang:
                if(mGoodBean.getCollectState() == 1){
                    postIsCollect(0);
                }else{
                    postIsCollect(1);
                }
                break;
            case R.id.img_add:
                num++;
                tv_sum.setText(num+"");
                updateTotalPriceAndNumber(num);
                break;
            case R.id.img_jian:
                if (num == 0){
                    Toast.makeText(this,"不能再少啦！",Toast.LENGTH_SHORT).show();
                    return;
                }
                num--;
                tv_sum.setText(num+"");
                updateTotalPriceAndNumber(num);
                break;
            case R.id.tv_bottompay: // 确认下单
                if(!TextUtils.isEmpty(PreferencesUtils.getString(GoodDetailActivity.this, "orderNumber"))){
                    Toast.makeText(GoodDetailActivity.this, "请先完成当前订单后方可再次点餐", Toast.LENGTH_SHORT).show();
                    return;
                }
                postOrder();
                break;
            default:
                break;
        }
    }

    private void postIsCollect(int isCollect){
        HttpHelper.getInstance().setIsCollect(this, mGoodBean.getId(), isCollect, new JsonHandler<String>(){
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                JSONObject object = null;
                int state = 0;
                try {
                    object = new JSONObject(responseString);
                    state = object.optInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(state == 100 && mGoodBean.getCollectState() == 1){
                    mGoodBean.setCollectState(0);
                    img_shoucang.setBackgroundResource(R.drawable.icon_heart_grey);
                    Toast.makeText(GoodDetailActivity.this,"已取消收藏",Toast.LENGTH_SHORT).show();
                }else if(state == 100 && mGoodBean.getCollectState() == 0){
                    mGoodBean.setCollectState(1);
                    img_shoucang.setBackgroundResource(R.drawable.icon_heart);
                    Toast.makeText(GoodDetailActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void postOrder(){
        HttpHelper.getInstance().comfirmBtn(this, mGoodBean.getPrice(), mGoodBean.getShopId(),
                "["+mGoodBean.toString()+"]", //待修改jsonProductList
                new JsonHandler<String>() {

                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                        Log.d(TAG,"postOrder()==onSuccess()"+responseString);
                        int state = ToolUtils.getNetBackCode(responseString);
                        String result = ToolUtils.getJsonParseResult(responseString);
                        if(state == 100){
                            PreferencesUtils.putString(GoodDetailActivity.this,"orderNumber",result);
                            GoodDetailActivity.this.finish();
                        }else{
                            Toast.makeText(GoodDetailActivity.this,"加入点餐列表失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                        Log.d(TAG,"postOrder()==onFailure()"+responseString);
                        Toast.makeText(GoodDetailActivity.this,"确认下单失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }
                });
    }

    /**
     * 获取评商品评论列表
     */
    private void getGoodsCommentsList(){
        HttpHelper.getInstance().getCommitsListById(this, mGoodBean.getShopId(), mGoodBean.getId(), 1, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                Log.d(TAG,"onSuccess()=getGoodsCommentsList=>>>"+responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if(state == 100){
                    JSONArray mArray;
                    JSONObject mObj;
                    try{
                        mArray = new JSONArray(result);
                        if(mArray != null && mArray.length() > 0){
                            for(int i=0;i<mArray.length();i++){
                                CommentBean commentBean = new CommentBean();
                                mObj = mArray.getJSONObject(i);
                                commentBean.setAccountUserName(mObj.getString("accountUserName"));
                                commentBean.setCommentTime(mObj.getString("commentTime"));
                                commentBean.setCommentStar(mObj.getInt("commentStart"));
                                commentBean.setCommentContent(mObj.getString("commentContent"));
                                myList.add(commentBean);
                            }
                            initCommentsList();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(GoodDetailActivity.this,"获取商品评论列表失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                Log.d(TAG,"onFailure()=getGoodsCommentsList=>>>"+responseString);
                Toast.makeText(GoodDetailActivity.this,"获取商品评论列表失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    private void getTcList(){
        Log.d(TAG,"productId=>>"+mGoodBean.getId());
        HttpHelper.getInstance().getTcList(this, mGoodBean.getId(), new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                Log.d(TAG,"onSuccess()=getTcList=>>>"+responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if(state == 100){
                    JSONArray mArray;
                    try {
                        mArray = new JSONArray(result);
                        if(mArray != null && mArray.length() > 0){
                            for(int i=0;i<mArray.length();i++){
                                TcBean tcBean = new TcBean();
                                tcBean.setImgurl(mArray.getJSONObject(i).getString("cImgUrl"));
                                tcBean.setName(mArray.getJSONObject(i).getString("cName"));
                                tcBean.setNum(mArray.getJSONObject(i).getInt("cQuantity"));
                                tcBean.setPrice(mArray.getJSONObject(i).getDouble("cPrice"));
                                mList.add(tcBean);
                            }
                            isShowTaoCan(true);
                        }else{
                            isShowTaoCan(false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(GoodDetailActivity.this,"加载套餐列表失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                Log.d(TAG,"onFailure()=getTcList=>>>"+responseString);
                Toast.makeText(GoodDetailActivity.this,"加载套餐列表失败",Toast.LENGTH_SHORT).show();
                isShowTaoCan(false);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

}
