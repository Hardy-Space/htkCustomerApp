package com.hl.htk_customer.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.FragmentAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.dialog.ShopStateDialog;
import com.hl.htk_customer.entity.ShopInfoEntity;
import com.hl.htk_customer.fragment.waimai.EvaluateFragment;
import com.hl.htk_customer.fragment.waimai.ItemFragment;
import com.hl.htk_customer.model.ShopInfoModel;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.FastBlurUtil;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21.
 * 外卖店铺详情
 */

public class WmShopDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.sdv_shop_logo)
    SimpleDraweeView sdvShopLogo;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_deliveryFee)
    TextView tvDeliveryFee;
    @Bind(R.id.tv_gonggao)
    TextView tv_gonggao;
    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.tv_rest)
    TextView tvRest;
    @Bind(R.id.tv_member)
    TextView tvMember;

    private List<String> titles;
    private List<BaseFragment> fragmentList;
    public static int shopId = -1;
    private ShopInfoEntity shopInfoEntity;
    private ShopStateDialog shopStateDialog;

    Dialog dialog;

    ImageThread mImageThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_wm_shop);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mImageThread != null){
            mImageThread.getLooper().quit();
            mImageThread = null;
        }
    }

    private void initWidget() {
        shopId = getIntent().getIntExtra("shopId", -1);
        llReturn.setOnClickListener(this);
        rlTitle.setOnClickListener(this);
        tvMember.setOnClickListener(this);

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                MyUtils.setIndicator(mTabLayout, 60, 60);
            }
        });


        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.shangpin));
        titles.add(getResources().getString(R.string.pingjia));

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        Bundle mBundler = new Bundle();
        mBundler.putInt("shopId", shopId);
        ItemFragment mItemFragment = new ItemFragment();
        mItemFragment.setArguments(mBundler);
        fragmentList = new ArrayList<>();
        fragmentList.add(mItemFragment);
        fragmentList.add(new EvaluateFragment());

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, fragmentList);

        //给viewpager设置适配器
        viewPager.setAdapter(fragmentAdapter);
        //把TabLayout 和ViewPager串联起来
        mTabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);

        showLoadingDialog();
        getShopInfo(shopId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_title:
                if (shopInfoEntity != null && shopInfoEntity.getData() != null) {
                    Intent intent = new Intent(WmShopDetailActivity.this, WmShopInfoActivity.class);
                    intent.putExtra("info", shopInfoEntity.getData());
                    intent.putExtra("shopId", shopId);
                    startActivity(intent);
                }
                break;
            case R.id.tv_member:
                if (TextUtils.isEmpty(new UserInfoManager(mContext).getToken())) return;
                goWebActivity(shopId);
                break;
            default:
                break;
        }
    }

    /**
     * 进入会员尊享平台
     * @param shopId
     */
    private void goWebActivity(int shopId) {
        Intent intent = new Intent(this , MemberPlatformActivity.class);
        intent.putExtra("shopId", shopId);
        startActivity(intent);
    }


    private void getShopInfo(int shopId) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.shopInfo, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {

                Log.i(TAG , rawJsonResponse);
                hideLoadingDialog();
                Gson gson = new Gson();
                shopInfoEntity = gson.fromJson(rawJsonResponse, ShopInfoEntity.class);

                if (shopInfoEntity.getCode() == 100) {
                    initData(shopInfoEntity);
                }
            }
        });

    }


    private void initData(final ShopInfoEntity shopInfoEntity) {

        ShopInfoModel.setShopName(shopInfoEntity.getData().getShopName());
        ShopInfoModel.setUrl(shopInfoEntity.getData().getLogoUrl());

        getIntent().putExtra("deliveryFee" , shopInfoEntity.getData().getDeliveryFee());

        if (shopInfoEntity.getData().getState() == 0) {
            //休息中
            ShopStateDialog shopStateDialog = new ShopStateDialog(this);
            shopStateDialog.show();
            tvRest.setVisibility(View.VISIBLE);
        }

        //   tvShopName.setText(shopInfoEntity.getData().getShopName());
        try {
            sdvShopLogo.setImageURI(Uri.parse(shopInfoEntity.getData().getLogoUrl()));

            //商家详情头部高斯模糊
            mImageThread = new ImageThread("mohu");
            mImageThread.start();

        } catch (Exception e) {

        }


        tvDeliveryFee.setText(shopInfoEntity.getData().getShopName());

        double deliveryFee = shopInfoEntity.getData().getDeliveryFee();
        if (deliveryFee == 0) {
            tv_gonggao.setText(getResources().getText(R.string.free));
        } else {
            tv_gonggao.setText("配送费：" + deliveryFee + "元");
        }

        List<ShopInfoEntity.DataBean.ShopBulletinBean> shopConsumptionActivities = shopInfoEntity.getData().getShopConsumptionActivities();
        if (shopConsumptionActivities == null) return;
        for (int i = 0; i < shopConsumptionActivities.size(); i++) {

            addView(shopConsumptionActivities.get(i).getContent());

        }

    }


    private void addView(String content) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_flipper, null);

        viewFlipper.addView(view);

        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText(content);
    }

    class ImageThread extends HandlerThread{

        public ImageThread(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            Bitmap getUrlBitmap;
            if (shopInfoEntity.getData().getLogoUrl() != null){
                getUrlBitmap = FastBlurUtil.GetUrlBitmap(shopInfoEntity.getData().getLogoUrl(), 10);
            }else {
                getUrlBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_new);
            }

            if (getUrlBitmap != null) {
                final Bitmap toBlur = FastBlurUtil.toBlur(getUrlBitmap, 10);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rlTitle.setBackgroundDrawable(new BitmapDrawable(toBlur));
                        // TODO: 2017/9/8 高斯模糊需要完善
                    }
                });
            }
        }

    }

}
