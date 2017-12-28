package com.hl.htk_customer.activity.diancan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.buffetmeal.BuffetMealPagerAdapter;
import com.hl.htk_customer.adapter.FragmentAdapter;
import com.hl.htk_customer.adapter.HorizontalAdopter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.entity.DianCanFenLeiEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/18.
 */

public class DianCanActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    ImageView tvRight;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.rl_right)
    RelativeLayout rlRight;
    @Bind(R.id.tv_mark)
    TextView tvMark;
    @Bind(R.id.iv_list)
    ImageView ivList;

    HorizontalAdopter horizontalAdopter;
    List<BaseFragment> myFragmentList;
    private List<String> titles;
    private String ScanResult = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diancan);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        initWidget();
        initData();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.diancan));
        ScanResult = getIntent().getStringExtra("ScanResult");

        horizontalAdopter = new HorizontalAdopter(this);

        llReturn.setOnClickListener(this);
        rlRight.setOnClickListener(this);
        ivList.setOnClickListener(this);

    }


    private void initData() {

        final RequestParams params = AsynClient.getRequestParams();
        params.put("qrKey", ScanResult);
        AsynClient.post(MyHttpConfing.diancanFenlei, this, params, new GsonHttpResponseHandler() {
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
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                DianCanFenLeiEntity dianCanFenLeiEntity = gson.fromJson(rawJsonResponse, DianCanFenLeiEntity.class);

                if (dianCanFenLeiEntity.getCode() == 100) {
                    if (dianCanFenLeiEntity.getData() == null) return;
                    horizontalAdopter.setData(dianCanFenLeiEntity.getData());
                    titles = new ArrayList<>();
                    myFragmentList = new ArrayList<BaseFragment>();
                    for (int j = 0; j < dianCanFenLeiEntity.getData().size(); j++) {
                        String categoryName = dianCanFenLeiEntity.getData().get(j).getCategoryName();
                        titles.add(categoryName);
                        mTabLayout.addTab(mTabLayout.newTab().setText(categoryName));

                        int id = dianCanFenLeiEntity.getData().get(j).getId();
                        myFragmentList.add(ListFragment.newInstance(id));
                    }

                    FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, myFragmentList);

                    //给viewpager设置适配器
                    viewPager.setAdapter(fragmentAdapter);
                    //把TabLayout 和ViewPager串联起来
                    mTabLayout.setupWithViewPager(viewPager);
                    //给TabLayout设置适配器
                    mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
                    viewPager.setCurrentItem(0);
                    viewPager.setOffscreenPageLimit(0);

                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_right:
                if (MyApplication.diancanList.size() == 0) return;
                double price = 0;
                for (int i = 0; i < MyApplication.diancanList.size(); i++) {
                    price = price + (MyApplication.diancanList.get(i).getPrice() * MyApplication.diancanList.get(i).getChooseNum());
                }
                Intent intent = new Intent(DianCanActivity.this, ConfirmZiZhuOrderActivity.class);
                intent.putExtra("price", price);
                startActivity(intent);
                break;
            case R.id.iv_list:
                startActivity(new Intent(DianCanActivity.this, OrderListActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        MyApplication.diancanList.clear();
    }

    @Subscribe
    public void onEventMainThread(DianCanEntity event) {
        if (event.isChanged()) {

            try {
                if (MyApplication.diancanList.size() == 0) {
                    tvMark.setVisibility(View.GONE);
                } else {
                    tvMark.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
