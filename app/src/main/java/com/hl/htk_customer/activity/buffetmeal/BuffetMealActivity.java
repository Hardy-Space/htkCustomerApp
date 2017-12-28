package com.hl.htk_customer.activity.buffetmeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanFenLeiEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 *
 */

public class BuffetMealActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.toolBar_buffet_meal)
    Toolbar toolbar;
    @Bind(R.id.tabLayout_buffet_meal)
    SlidingTabLayout tabLayoutBuffetMeal;
    @Bind(R.id.vp_buffet_meal)
    ViewPager vpBuffetMeal;

    BuffetMealPagerAdapter mBuffetMealPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffet_meal);
        ButterKnife.bind(this);

        initBar();
        initViewPager();
    }

    private void initBar() {
        title.setText("自助点餐");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        String scanResult = getIntent().getStringExtra("ScanResult");
        getTabData(scanResult);
    }

    private void getTabData(String scanResult){
        RequestParams params = AsynClient.getRequestParams();
        params.put("qrKey", scanResult);
        AsynClient.post(MyHttpConfing.diancanFenlei, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {

            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                DianCanFenLeiEntity dianCanFenLeiEntity = gson.fromJson(rawJsonResponse, DianCanFenLeiEntity.class);
                if (dianCanFenLeiEntity.getCode() == 100){
                    mBuffetMealPagerAdapter = new BuffetMealPagerAdapter(getSupportFragmentManager() , dianCanFenLeiEntity.getData());
                    vpBuffetMeal.setAdapter(mBuffetMealPagerAdapter);
                    tabLayoutBuffetMeal.setViewPager(vpBuffetMeal);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
