package com.hl.htk_customer.activity.buffetmeal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanDetailEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/24.
 */

public class BuffetMealGoodsDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_buffet_meal_goods_icon)
    SimpleDraweeView ivBuffetMealGoodsIcon;
    @Bind(R.id.tv_buffet_meal_goods_title)
    TextView tvBuffetMealGoodsTitle;
    @Bind(R.id.tv_buffet_meal_goods_sales)
    TextView tvBuffetMealGoodsSales;
    @Bind(R.id.tv_buffet_meal_goods_price)
    TextView tvBuffetMealGoodsPrice;
    @Bind(R.id.tv_buffet_meal_goods_cut)
    TextView tvBuffetMealGoodsCut;
    @Bind(R.id.tv_buffet_meal_goods_choose_num)
    TextView tvBuffetMealGoodsChooseNum;
    @Bind(R.id.tv_buffet_meal_goods_add)
    TextView tvBuffetMealGoodsAdd;
    @Bind(R.id.tv_buffet_meal_goods_introduction)
    TextView tvBuffetMealGoodsIntroduction;

    private static String PRODUCTID = "PRODUCTID";
    private static String CHOOSE_NUM = "CHOOSE_NUM";
    private int chooseNum;

    public static void launch(Activity activity, int productId, int chooseNum) {
        Intent intent = new Intent(activity, BuffetMealGoodsDetailsActivity.class);
        intent.putExtra(PRODUCTID, productId);
        intent.putExtra(CHOOSE_NUM, chooseNum);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffet_meal_goods_details);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        title.setText("菜品详情");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int productId = intent.getIntExtra(PRODUCTID, 0);
        chooseNum = intent.getIntExtra(CHOOSE_NUM, 0);
        setProductNum();

        imgBack.setOnClickListener(this);
        tvBuffetMealGoodsCut.setOnClickListener(this);
        tvBuffetMealGoodsAdd.setOnClickListener(this);

        getDetails(productId);
    }

    private void setProductNum() {
        tvBuffetMealGoodsChooseNum.setText(String.valueOf(chooseNum));
    }

    private void getDetails(int productId) {
        showLoadingDialog();
        RequestParams params = AsynClient.getRequestParams();
        params.put("productId", productId);
        AsynClient.post(MyHttpConfing.detail, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                hideLoadingDialog();
                Gson gson = new Gson();

                DianCanDetailEntity dianCanDetailEntity = gson.fromJson(rawJsonResponse, DianCanDetailEntity.class);

                if (dianCanDetailEntity.getCode() == 100) {
                    if (dianCanDetailEntity.getData() == null) return;
                    setData(dianCanDetailEntity.getData());
                }
            }
        });
    }

    private void setData(DianCanDetailEntity.DataBean data) {
        ivBuffetMealGoodsIcon.setImageURI(Uri.parse(data.getImgUrl()));
        tvBuffetMealGoodsTitle.setText(data.getProductName());
        tvBuffetMealGoodsSales.setText(String.format(getString(R.string.join_sales_of_month), data.getMonthlySalesVolume()));
        tvBuffetMealGoodsPrice.setText(String.format(getString(R.string.join_order_money), data.getPrice()));
        if (data.getDescription() != null)
            tvBuffetMealGoodsIntroduction.setText(data.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_buffet_meal_goods_cut:
                if (chooseNum <= 0) return;
                chooseNum--;
                setProductNum();
                break;
            case R.id.tv_buffet_meal_goods_add:
                if (chooseNum >= 50) return;
                chooseNum++;
                setProductNum();
                break;
        }
    }
}
