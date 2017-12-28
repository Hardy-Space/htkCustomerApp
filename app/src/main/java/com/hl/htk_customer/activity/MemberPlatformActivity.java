package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.MemberPlatformAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.MemberPlatformEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MemberPlatformActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_member_platform_content)
    RecyclerView rvMemberPlatformContent;
    @Bind(R.id.tv_member_platform_reserved_seats)
    TextView tvMemberPlatformReservedSeats;
    @Bind(R.id.tv_member_platform_join_members)
    TextView tvMemberPlatformJoinMembers;
    @Bind(R.id.tv_member_platform_mine)
    TextView tvMemberPlatformMine;
    @Bind(R.id.refresh_member_platform_content)
    SmartRefreshLayout refreshMemberPlatformContent;

    private int pageNumber = 1;
    private MemberPlatformAdapter memberPlatformAdapter;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_platform);

        ButterKnife.bind(this);

        initToolBar();
        init();
    }

    private void initToolBar() {
        title.setText("会员尊享平台");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {

        shopId = getIntent().getIntExtra("shopId" , 0);

        tvMemberPlatformReservedSeats.setOnClickListener(this);
        tvMemberPlatformJoinMembers.setOnClickListener(this);
        tvMemberPlatformMine.setOnClickListener(this);

        refreshMemberPlatformContent.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNumber++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getData();
            }
        });

        rvMemberPlatformContent.setLayoutManager(new LinearLayoutManager(this));
        memberPlatformAdapter = new MemberPlatformAdapter(R.layout.item_member_platform, null);
        rvMemberPlatformContent.setAdapter(memberPlatformAdapter);

        memberPlatformAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MemberPlatformActivity.this , MyWebViewActivity.class);
                String url = memberPlatformAdapter.getData().get(position).getDetailRequestUrl() + "/" + memberPlatformAdapter.getData().get(position).getId();
                intent.putExtra("webUrl" , url);
                startActivity(intent);
            }
        });

        showLoadingDialog();
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        params.put("pageNum", pageNumber);
        AsynClient.post(MyHttpConfing.getMemberHomeListData, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                refreshMemberPlatformContent.finishLoadmore();
                refreshMemberPlatformContent.finishRefresh();
                hideLoadingDialog();
                Log.i(TAG, "onFailure: " + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                refreshMemberPlatformContent.finishLoadmore();
                refreshMemberPlatformContent.finishRefresh();
                hideLoadingDialog();
                MemberPlatformEntity memberPlatformEntity = new Gson().fromJson(rawJsonResponse, MemberPlatformEntity.class);
                if (memberPlatformEntity.getCode() == 100){
                    if (pageNumber == 1){
                        memberPlatformAdapter.setNewData(memberPlatformEntity.getData());
                    }else {
                        if (memberPlatformEntity.getData() != null)
                            memberPlatformAdapter.addData(memberPlatformEntity.getData());
                    }

                    if (memberPlatformEntity.getData() == null || memberPlatformEntity.getData().size() == 0){
                        refreshMemberPlatformContent.setEnableLoadmore(false);
                    }else{
                        if (memberPlatformEntity.getData().size() < 8){
                            refreshMemberPlatformContent.setEnableLoadmore(false);
                        }else {
                            refreshMemberPlatformContent.setEnableLoadmore(true);
                        }
                    }
                }else {
                    showMessage(memberPlatformEntity.getMessage());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_member_platform_reserved_seats:
                Intent intentReservedSeats = new Intent(MemberPlatformActivity.this, ReservedSeatsActivity.class);
                intentReservedSeats.putExtra("shopId" , shopId);
                startActivity(intentReservedSeats);
                break;
            case R.id.tv_member_platform_join_members:
                Intent intentJoinMembers = new Intent(MemberPlatformActivity.this, JoinMembersActivity.class);
                intentJoinMembers.putExtra("shopId" , shopId);
                startActivity(intentJoinMembers);
                break;
            case R.id.tv_member_platform_mine:
                Intent intentMine = new Intent(MemberPlatformActivity.this, MineActivity.class);
                intentMine.putExtra("shopId" , shopId);
                startActivity(intentMine);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
