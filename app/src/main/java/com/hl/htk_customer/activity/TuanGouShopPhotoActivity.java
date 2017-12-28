package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.TuanGouShopPhotoAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TuanGouShopPhotoEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/17.
 */

public class TuanGouShopPhotoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_photo)
    RecyclerView rvPhoto;
    private int shopId = -1;

    private GridLayoutManager mLayoutManager;
    private TuanGouShopPhotoAdapter adapter;
    private TuanGouShopPhotoEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangou_photo);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        setSupportActionBar(toolbar);
        title.setText("商家相册");
        imgBack.setOnClickListener(this);
        shopId = getIntent().getIntExtra("shopId", -1);
        mLayoutManager = new GridLayoutManager(TuanGouShopPhotoActivity.this, 3);//设置为一个3列的纵向网格布局
        rvPhoto.setLayoutManager(mLayoutManager);
        entity = new TuanGouShopPhotoEntity();
        getData();
    }

    private void getData() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        AsynClient.get(MyHttpConfing.getTuanGouShopPhoto, mContext, params, new GsonHttpResponseHandler() {

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + "code" + statusCode + "message" + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                entity = gson.fromJson(rawJsonResponse, TuanGouShopPhotoEntity.class);
                if (entity.getCode() == 100) {
                    if (entity.getData() == null || entity.getData().size() == 0) {
                        Toast.makeText(TuanGouShopPhotoActivity.this, "暂无图片", Toast.LENGTH_SHORT).show();
                    } else {
                        initInfo(entity.getData());
                    }
                }
            }
        });
    }

    private void initInfo(final ArrayList<TuanGouShopPhotoEntity.DataBean> datas) {
        Log.i(TAG, "datas.size()==>>>>"+datas.size());
        adapter = new TuanGouShopPhotoAdapter(TuanGouShopPhotoActivity.this, datas);
        rvPhoto.setAdapter(adapter);
        adapter.setOnItemClickListener(new TuanGouShopPhotoAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TuanGouShopPhotoActivity.this,PicViewerActivity.class);
                intent.putExtra("datas",datas);
                intent.putExtra("positon",position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
