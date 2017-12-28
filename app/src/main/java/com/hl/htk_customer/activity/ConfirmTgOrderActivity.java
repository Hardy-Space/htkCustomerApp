package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TaoCanDetailEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/14.
 *
 */

public class ConfirmTgOrderActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_shop_info)
    RelativeLayout rlShopInfo;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.tv_taocan_name)
    TextView tvTaocanName;
    @Bind(R.id.tv_price1)
    TextView tvPrice1;
    @Bind(R.id.tv_use_time)
    TextView tvUseTime;
    @Bind(R.id.rl_taocan_info)
    RelativeLayout rlTaocanInfo;
    @Bind(R.id.reduce)
    TextView reduce;
    @Bind(R.id.shoppingNum)
    TextView shoppingNum;
    @Bind(R.id.increase)
    TextView increase;
    @Bind(R.id.choiceLayout)
    LinearLayout choiceLayout;
    @Bind(R.id.rl_number)
    RelativeLayout rlNumber;
    @Bind(R.id.tv_price2)
    TextView tvPrice2;
    @Bind(R.id.rl_xiaoji)
    RelativeLayout rlXiaoji;
    @Bind(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;

    private int num = 1;
    private int packageId = -1;
    private int shopId = -1;
    private double price = 0;

    private String url = "";
    private String taocanName = "";

    private double oldPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tg_confirm_order);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
        getTaoCanDetail();
    }


    private void initWidget() {
        shoppingNum.setText(num + "");

        llReturn.setOnClickListener(this);
        reduce.setOnClickListener(this);
        increase.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    private void initViews(TaoCanDetailEntity taoCanDetailEntity) {
        TaoCanDetailEntity.DataBean data = taoCanDetailEntity.getData();
        TaoCanDetailEntity.DataBean.BuyPackageBean buyPackage = data.getBuyPackage();

        tvTitle.setText(data.getShopName());
        try {
            image.setImageURI(Uri.parse(buyPackage.getImgUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String order = "";
        if (buyPackage.isReservation()) {
            order = "需要预约";
        } else {
            order = "免预约";
        }

        tvTaocanName.setText(buyPackage.getPackageName());
        tvUseTime.setText(buyPackage.getUsageTime() + "|" + order);
        tvPrice1.setText("￥" + buyPackage.getPrice() + "");
        tvPrice2.setText("￥" + buyPackage.getPrice() + "");
        oldPrice = buyPackage.getPrice();
        price = buyPackage.getPrice();
        try {
            MyUtils.subPhoneNumber(app.getUserInfoManager().getPhone() + "", tvPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        taocanName = buyPackage.getPackageName();
        url = buyPackage.getImgUrl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.reduce:
                if (num == 1) {
                    showMessage("最少为一单");
                    return;
                } else {
                    num--;
                    shoppingNum.setText(String.valueOf(num));
                    price = oldPrice * num;
                    tvPrice2.setText(String.valueOf(price));
                }
                break;
            case R.id.increase:
                num++;
                shoppingNum.setText(String.valueOf(num));
                price = oldPrice * num;
                tvPrice2.setText(String.valueOf(price));
                break;
            case R.id.tv_submit:
                Intent intent = new Intent(ConfirmTgOrderActivity.this, TgGoPayActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("price", price + "");
                intent.putExtra("taocanName", taocanName);
                intent.putExtra("shopId", shopId);
                intent.putExtra("packageId", packageId);
                intent.putExtra("num", num);
                startActivity(intent);
                break;
            default:
                break;

        }
    }


    private void getTaoCanDetail() {

        shopId = getIntent().getIntExtra("shopId", -1);
        packageId = getIntent().getIntExtra("packageId", -1);

        RequestParams params = AsynClient.getRequestParams();
        params.put("packageId", packageId);
        AsynClient.post(MyHttpConfing.TuanGouTaoCanDetail, this, params, new GsonHttpResponseHandler() {
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
                TaoCanDetailEntity taoCanDetailEntity = gson.fromJson(rawJsonResponse, TaoCanDetailEntity.class);

                if (taoCanDetailEntity.getCode() == 100) {
                    initViews(taoCanDetailEntity);
                }

            }
        });

    }


}
