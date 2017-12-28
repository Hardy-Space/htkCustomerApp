package com.hl.htk_customer.activity.diancan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.DianCanDetailActivity;
import com.hl.htk_customer.adapter.YiDianConfigeAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.OneLineDialog;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.entity.OrderProduct;
import com.hl.htk_customer.entity.SeatEntity;
import com.hl.htk_customer.entity.SuccessEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class ConfirmZiZhuOrderActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.head)
    SimpleDraweeView head;
    @Bind(R.id.tv_seat)
    TextView tvSeat;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.myListView)
    MyListView myListView;
    @Bind(R.id.btn_pay)
    TextView btnPay;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.rl_seat)
    RelativeLayout rlSeat;
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_yidian)
    TextView tvYiDian;
    @Bind(R.id.et_remark)
    EditText etReMark;


    private YiDianConfigeAdapter adapter;
    private SeatEntity seatEntity;

    List<String> list = new ArrayList<>();
    private OneLineDialog payWayDialog;
    private String zuowei = "";
    private double price = 0;
    private String remark = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_confirm_zizhu);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.confirm_order));

        price = getIntent().getDoubleExtra("price", -1);
        tvPrice.setText("总计：￥" + price + "元");

        int size = 0;
        for (int i = 0; i < MyApplication.diancanList.size(); i++) {

            size += MyApplication.diancanList.get(i).getChooseNum();
        }

        tvDetail.setText(size + "份共" + price + "元");
        tvNumber.setText(size + "");
        tvYiDian.setText("已点 \n" + price + "元");
        head.setImageURI(Uri.parse(app.getUserInfoManager().getAvaUrl()));
        name.setText(app.getUserInfoManager().getNickName());
        adapter = new YiDianConfigeAdapter(this);
        myListView.setAdapter(adapter);
        adapter.setData(MyApplication.diancanList);
        payWayDialog = new OneLineDialog(this);
        getData();

        llReturn.setOnClickListener(this);
        rlSeat.setOnClickListener(this);
        btnPay.setOnClickListener(this);

        payWayDialog.setOngetDataListener(new OneLineDialog.getDataListener() {
            @Override
            public void getData(String time, int viewId) {
                tvSeat.setText(time);
                zuowei = time;
            }
        }, 1);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_seat:

                if (list.size() == 0) return;
                payWayDialog.setData(list);
                payWayDialog.setShowOne(list.get(0).toString());
                payWayDialog.show();

                break;
            case R.id.btn_pay:
                if (TextUtils.isEmpty(zuowei)) {
                    showMessage("请选择座位号");
                    return;
                }
                remark = etReMark.getText().toString();
                btnPay.setEnabled(false);
                xiadan();

                //  "remark"

           /*     Intent intent = new Intent(ConfirmZiZhuOrderActivity.this, ChoosePayStyleActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("seatName", zuowei);
                startActivity(intent);*/

                break;
            default:
                break;

        }
    }


    private void getData() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", MyApplication.shopId);
        AsynClient.post(MyHttpConfing.shopSeat, this, params, new GsonHttpResponseHandler() {
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
                seatEntity = gson.fromJson(rawJsonResponse, SeatEntity.class);

                if (seatEntity.getCode() == 100) {

                    if (seatEntity.getData() == null) return;

                    for (int i = 0; i < seatEntity.getData().size(); i++) {
                        list.add(seatEntity.getData().get(i).getSeatName());
                    }

                }

            }
        });

    }


    private void xiadan() {


        List<DianCanFenLeiListEntity.DataBean> diancanList = MyApplication.diancanList;

        if (diancanList == null) return;

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < diancanList.size(); i++) {
            DianCanFenLeiListEntity.DataBean dataBean = diancanList.get(i);
            orderProducts.add(new OrderProduct(dataBean.getProductName(), dataBean.getChooseNum(), dataBean.getPrice(), dataBean.getCategoryId()));
        }


        RequestParams params = AsynClient.getRequestParams();
        params.put("orderAmount", price);
        params.put("shopId", MyApplication.shopId);
        params.put("seatName", zuowei);
        params.put("remark", remark);
        Gson gson = new Gson();
        String s = gson.toJson(orderProducts);
        params.put("jsonProductList", s);

        AsynClient.post(MyHttpConfing.xiadan, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                btnPay.setEnabled(true);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                SuccessEntity successEntity = gson.fromJson(rawJsonResponse, SuccessEntity.class);
                if (successEntity.getCode() == 100) {
                    showMessage("下单成功");
                    int orderIds = successEntity.getData().getOrderId();
                    toDetail(orderIds);

                } else {
                    btnPay.setEnabled(true);
                    showMessage(successEntity.getMessage());
                }

            }
        });


    }


    private void toDetail(int orderIds) {


        List<Activity> activities = MyApplication.get();
        for (int i = 0; i < activities.size(); i++) {

            Activity activity = activities.get(i);
            if (activity instanceof ConfirmZiZhuOrderActivity) {
                if (activity != null) {
                    MyApplication.activities.remove(i);
                    activity.finish();
                }
            } else if (activity instanceof DianCanActivity) {
                if (activity != null) {
                    MyApplication.activities.remove(i);
                    activity.finish();
                }

            }

        }

        Intent intent = new Intent(ConfirmZiZhuOrderActivity.this, DianCanDetailActivity.class);
        intent.putExtra("orderId", orderIds);
        startActivity(intent);

        finish();


    }

}
