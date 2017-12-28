package com.hl.htk_customer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.EvluateAdapter;
import com.hl.htk_customer.adapter.TuanGouTaoCanAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.ImageModel;
import com.hl.htk_customer.entity.TgGuangGaoEntity;
import com.hl.htk_customer.entity.TuanGouShopInfoEntity;
import com.hl.htk_customer.entity.TuanGouShopPhotoEntity;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.ColorAlphaUtils;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.StateBarCompat;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyRatingBar;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * <p>
 * 团购 商家
 */

public class TuanGouShopActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.tuangou_shop_appbar)
    AppBarLayout mAppBar;
    @Bind(R.id.tuangou_shop_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tuangou_shop_title)
    TextView mTitle;
    @Bind(R.id.tuangou_shop_title_bg)
    SimpleDraweeView mTitleBg;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.iv_collection)
    ImageView ivCollection;
    @Bind(R.id.ratingBar)
    MyRatingBar ratingBar;
    @Bind(R.id.tv_mean_price)
    TextView tvMeanPrice;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.iv_call)
    ImageView ivCall;
    @Bind(R.id.tv_evaluation_num)
    TextView tvEvaluationNum;
    @Bind(R.id.tv_all_evaluate)
    TextView tvAllEvaluate;
    @Bind(R.id.tuangou_shop_evaluation_rv)
    RecyclerView mEvaluateRv;
    @Bind(R.id.tuangou_shop_product_rv)
    RecyclerView mProductRv;
    @Bind(R.id.tv_member)
    TextView tvMember;

    TuanGouTaoCanAdapter tuanGouTaoCanAdapter;
    EvluateAdapter evluateAdapter;
    List<ImageModel> imageList = new ArrayList<>();
    private int shopId = -1;
    private int mark = -1; // 收藏标记
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangou_shop);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initWidget() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTitle.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        tvAllEvaluate.setOnClickListener(this);
        tvEvaluationNum.setOnClickListener(this);
        mTitleBg.setOnClickListener(this);
        tvMember.setOnClickListener(this);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float offset = Math.abs(verticalOffset * 1.0f/appBarLayout.getTotalScrollRange());
                Log.i(TAG, "onOffsetChanged: " + offset);
                mToolbar.setBackgroundColor(ColorAlphaUtils.changeAlpha(getResources().getColor(R.color.colorBlue) ,offset));
                mTitle.setTextColor(ColorAlphaUtils.changeAlpha(getResources().getColor(R.color.colorWhite) ,offset));
                if (offset != 0)
                    StateBarCompat.compat(TuanGouShopActivity.this , ColorAlphaUtils.changeAlpha(getResources().getColor(R.color.colorBlue) ,offset));

            }
        });

        mProductRv.setLayoutManager(new LinearLayoutManager(this));
        mProductRv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        tuanGouTaoCanAdapter = new TuanGouTaoCanAdapter(R.layout.item_tg_taocan , null);
        mProductRv.setAdapter(tuanGouTaoCanAdapter);

        mEvaluateRv.setLayoutManager(new LinearLayoutManager(this));
        mEvaluateRv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        evluateAdapter = new EvluateAdapter(R.layout.item_evaluate , null);
        mEvaluateRv.setAdapter(evluateAdapter);

        shopId = getIntent().getIntExtra("shopId", -1);

        getGuangGao();  //banner 图
        getShopInfo();  //店铺信息
        getEvaluate();  //评价

        initListener();
    }

    private void initListener(){
        tuanGouTaoCanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TuanGouShopInfoEntity.DataBean.BuyPackageListBean item = (TuanGouShopInfoEntity.DataBean.BuyPackageListBean) adapter.getData().get(position);
                Intent intent = new Intent(TuanGouShopActivity.this, TgTaoCanDetailActivity.class);
                intent.putExtra("shopId", item.getShopId());
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_evaluation_num:
                Intent intent = new Intent(this, WmEvaluateActivity.class);
                intent.putExtra("shopId", shopId);
                intent.putExtra("mark", 1);
                startActivity(intent);
                break;
            case R.id.tuangou_shop_title:
                finish();
                break;
            case R.id.iv_collection:
                if (mark == -1) return;
                collection();
                break;
            case R.id.iv_call:
                if (TextUtils.isEmpty(phoneNumber)) return;
                MyUtils.call(TuanGouShopActivity.this, phoneNumber);
                break;
            case R.id.tv_all_evaluate:
                Intent intent1 = new Intent(this, WmEvaluateActivity.class);
                intent1.putExtra("shopId", shopId);
                intent1.putExtra("mark", 1);
                startActivity(intent1);
                break;

            case R.id.tuangou_shop_title_bg:
                Intent intent2 = new Intent(this, TuanGouShopPhotoActivity.class);
                intent2.putExtra("shopId", shopId);
                startActivity(intent2);
                break;

            case R.id.tv_member:
                if (TextUtils.isEmpty(new UserInfoManager(mContext).getToken())){
                    return;
                }else {
                    Intent intent3 = new Intent(this , MemberPlatformActivity.class);
                    intent3.putExtra("shopId", shopId);
                    startActivity(intent3);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
//            case R.id.listView_taocan:
//                TuanGouShopInfoEntity.DataBean.BuyPackageListBean item = (TuanGouShopInfoEntity.DataBean.BuyPackageListBean) tuanGouTaoCanAdapter.getItem(position);
//                Intent intent = new Intent(TuanGouShopActivity.this, TgTaoCanDetailActivity.class);
//                intent.putExtra("shopId", item.getShopId());
//                intent.putExtra("id", item.getId());
//                startActivity(intent);
//                break;
            default:
                break;
        }
    }



    private void getGuangGao() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.TgShopGuangGao, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                TgGuangGaoEntity tgGuangGaoEntity = gson.fromJson(rawJsonResponse, TgGuangGaoEntity.class);
                if (tgGuangGaoEntity.getCode() == 100) {
                    if (tgGuangGaoEntity.getData() == null) return;
                    for (int i = 0; i < tgGuangGaoEntity.getData().size(); i++) {
                        TgGuangGaoEntity.DataBean dataBean = tgGuangGaoEntity.getData().get(i);
                        imageList.add(new ImageModel(dataBean.getImgUrl(), dataBean.getShopId()));
                    }
//                    MyBanner myBanner = new MyBanner(vpHomeBanner, textBanner, imageList, TuanGouShopActivity.this);
//                    myBanner.initMyBanner();

                    if (tgGuangGaoEntity.getData().size() >= 1)
                        mTitleBg.setImageURI(Uri.parse(tgGuangGaoEntity.getData().get(0).getImgUrl()));
                }
            }
        });
    }


    private void getShopInfo() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("mark", 1);
        AsynClient.post(MyHttpConfing.TuanGouShopInfo, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                TuanGouShopInfoEntity tuanGouShopInfoEntity = gson.fromJson(rawJsonResponse, TuanGouShopInfoEntity.class);
                if (tuanGouShopInfoEntity.getCode() == 100) {
                    initViewDatas(tuanGouShopInfoEntity);
                }
            }
        });
    }

    private void initViewDatas(TuanGouShopInfoEntity tuanGouShopInfoEntity) {
        if (tuanGouShopInfoEntity.getData() == null) return;
        TuanGouShopInfoEntity.DataBean data = tuanGouShopInfoEntity.getData();
        phoneNumber = data.getPhone();
        tvShopName.setText(data.getShopName());
        tvMeanPrice.setText("人均：" + data.getPerCapitaPrice());
        ratingBar.setCountSelected((int) data.getScore());
        tvAddress.setText(data.getLocationAddress());
        tuanGouTaoCanAdapter.setNewData(data.getBuyPackageList());
        if (data.isCollection()) {
            mark = 0;
            ivCollection.setImageResource(R.mipmap.collection2);
        } else {
            mark = 1;
            ivCollection.setImageResource(R.mipmap.collection1);
        }
    }


    private void getEvaluate() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("mark", 1);
        params.put("pageNumber", 1);
        AsynClient.post(MyHttpConfing.shopEvaluate, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                WmEvaluateEntity wmEvaluateEntity = gson.fromJson(rawJsonResponse, WmEvaluateEntity.class);
                if (wmEvaluateEntity.getCode() == 100) {
                    List<WmEvaluateEntity.DataBean.ListBean> list = new ArrayList<WmEvaluateEntity.DataBean.ListBean>();
                    tvEvaluationNum.setText("用户评价(" + wmEvaluateEntity.getData().getCount() + ")");
                    if (wmEvaluateEntity.getData().getList() == null) {
                        tvEvaluationNum.setText("用户评价(" + 0 + ")");
                        return;
                    }
                    int size = wmEvaluateEntity.getData().getList().size();
                    if (size > 5) {
                        size = 5;
                    }
                    for (int i = 0; i < size; i++) {
                        list.add(wmEvaluateEntity.getData().getList().get(i));
                    }
                    evluateAdapter.setNewData(list);
                }
            }
        });
    }

    private void collection() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("mark", mark);   //0取消收藏   1收藏
        AsynClient.post(MyHttpConfing.collection, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100) {
                    if (mark == 1) {
                        ivCollection.setImageResource(R.mipmap.collection2);
                        mark = 0;
                    } else {
                        ivCollection.setImageResource(R.mipmap.collection1);
                        mark = 1;
                    }
                }
            }
        });
    }
}
