package com.hl.htk_customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AlPayEntity;
import com.hl.htk_customer.entity.SuccessEntity;
import com.hl.htk_customer.entity.WxPaySuccessEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.utils.pay.AliPayTg;
import com.hl.htk_customer.utils.pay.PayStyle;
import com.hl.htk_customer.utils.pay.WeChatPayTg;
import com.hl.htk_customer.wxapi.PayResult;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import wxapi.WeiChat;

/**
 * Created by Administrator on 2017/7/14.
 */

public class TgGoPayActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_shop_info)
    RelativeLayout rlShopInfo;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_taocan_name)
    TextView tvTaocanName;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv_choose1)
    TextView tvChoose1;
    @Bind(R.id.rl_al)
    RelativeLayout rlAl;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv_choose2)
    TextView tvChoose2;
    @Bind(R.id.rl_wx)
    RelativeLayout rlWx;
    @Bind(R.id.tv_submit)
    Button tvSubmit;

    private int tag = -1;  // 1支付宝  2微信

    private String url = "";
    private String price = "";
    private String taocanName = "";

    private int shopId = -1;
    private int packageId = -1;
    private int num = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gou_pay);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initWidget() {

        shopId = getIntent().getIntExtra("shopId", -1);
        packageId = getIntent().getIntExtra("packageId", -1);
        price = getIntent().getStringExtra("price");
        url = getIntent().getStringExtra("url");
        taocanName = getIntent().getStringExtra("taocanName");
        num = getIntent().getIntExtra("num", -1);

        tvPrice.setText("￥" + price);
        tvTaocanName.setText(taocanName);
        try {
            image.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

        llReturn.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        rlWx.setOnClickListener(this);
        rlAl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_submit:

                if (tag == -1) {
                    showMessage("请选择支付方式");
                    return;
                }

                PayStyle pay;
                if (tag == 1) {
                    pay = new AliPayTg(TgGoPayActivity.this , price , String.valueOf(shopId) ,
                            new UserInfoManager(mContext).getNickName() , num , String.valueOf(packageId) , tvSubmit);
                    pay.pay();
                } else if (tag == 2) {
                    pay = new WeChatPayTg(TgGoPayActivity.this , price , String.valueOf(shopId) ,
                            new UserInfoManager(mContext).getNickName() , num , String.valueOf(packageId) , tvSubmit);
                    pay.pay();
                }

                break;
            case R.id.rl_wx:
                tag = 2;
                clear();
                tvChoose2.setBackgroundResource(R.mipmap.xuanze04);
                break;
            case R.id.rl_al:
                tag = 1;
                clear();
                tvChoose1.setBackgroundResource(R.mipmap.xuanze04);
                break;
            default:
                break;


        }
    }

    private void clear() {
        tvChoose2.setBackgroundResource(R.mipmap.xuanze03);
        tvChoose1.setBackgroundResource(R.mipmap.xuanze03);
    }

}
