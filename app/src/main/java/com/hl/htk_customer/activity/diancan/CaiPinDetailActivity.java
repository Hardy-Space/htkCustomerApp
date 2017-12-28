package com.hl.htk_customer.activity.diancan;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanDetailEntity;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/22.
 */

public class CaiPinDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.imageView)
    SimpleDraweeView imageView;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_yueshou)
    TextView tvYueshou;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_remove)
    TextView tvRemove;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.iv_zan)
    ImageView iv_zan;
    @Bind(R.id.tv_jianjie)
    TextView tv_jianjie;
    @Bind(R.id.zan_num)
    TextView  zan_num;

    private int num;
    //  private DianCanFenLeiListEntity.FunctionType dataBean;
    private int id = -1;
    private DianCanDetailEntity dianCanDetailEntity;
    private DianCanDetailEntity.DataBean dataBean;
    private int zanTag = 0;  //0 未点赞  1 已点赞

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_caipin_xiangqing);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        // id =  getIntent().getSerializableExtra("id");
        id = getIntent().getIntExtra("id", -1);
        num = getIntent().getIntExtra("num", -1);
        getData();
     /*   tvTitle.setText(dataBean.getProductName());
        imageView.setImageURI(Uri.parse(dataBean.getImgUrl()));
        tvName.setText(dataBean.getProductName());
        tvYueshou.setText("月售" + dataBean.getMonthlySalesVolume() + "单");
        tvPrice.setText(dataBean.getPrice() + "元");
        num = dataBean.getChooseNum();
        tvNum.setText(num + "");*/

        llReturn.setOnClickListener(this);
        tvRemove.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        iv_zan.setOnClickListener(this);
    }


    private void getData() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("productId", id);
        AsynClient.post(MyHttpConfing.detail, this, params, new GsonHttpResponseHandler() {
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

                dianCanDetailEntity = gson.fromJson(rawJsonResponse, DianCanDetailEntity.class);

                if (dianCanDetailEntity.getCode() == 100) {
                    if (dianCanDetailEntity.getData() == null) return;
                    bindData();
                }


            }
        });

    }

    private void bindData() {
        dataBean = dianCanDetailEntity.getData();
        tvTitle.setText(dataBean.getProductName());
        imageView.setImageURI(Uri.parse(dataBean.getImgUrl()));
        tvName.setText(dataBean.getProductName());
        tvYueshou.setText("月售" + dataBean.getMonthlySalesVolume() + "单");
        tvPrice.setText(dataBean.getPrice() + "元");
        tv_jianjie.setText(dataBean.getProductDetail());
        tvNum.setText(num + "");
        zan_num.setText(dataBean.getLikeQuantity()+"");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_add:
                num++;
                tvNum.setText(num + "");

                if (num == 1) {
                    MyApplication.diancanList.add(new DianCanFenLeiListEntity.
                            DataBean(dataBean.getId(), dataBean.getProductName(), dataBean.getPrice(), dataBean.getImgUrl(), dataBean.getMonthlySalesVolume(), dataBean.getCategoryId(), num));

                } else {

                    for (int i = 0; i < MyApplication.diancanList.size(); i++) {

                        List<DianCanFenLeiListEntity.DataBean> diancanList = MyApplication.diancanList;

                        if (diancanList.get(i).getProductName().equals(dataBean.getProductName())) {
                            MyApplication.diancanList.get(i).setChooseNum(num);
                            break;
                        }
                    }
                }


                EventBus.getDefault().post(new DianCanEntity(true));


                break;
            case R.id.tv_remove:
                if (num == 0) return;
                num--;
                tvNum.setText(num + "");


                if (num == 0) {
                    for (int j = 0; j < MyApplication.diancanList.size(); j++) {
                        String productName = MyApplication.diancanList.get(j).getProductName();
                        if (productName.equals(dataBean.getProductName())) {
                            MyApplication.diancanList.remove(j);
                            break;
                        }
                    }
                } else {

                    for (int i = 0; i < MyApplication.diancanList.size(); i++) {

                        String productName = MyApplication.diancanList.get(i).getProductName();
                        if (productName.equals(dataBean.getProductName())) {
                            MyApplication.diancanList.get(i).setChooseNum(num);
                            break;
                        }

                    }

                }


                EventBus.getDefault().post(new DianCanEntity(true));
                break;

            case R.id.iv_zan:

                if (zanTag == 0) {
                    zan();
                } else {
                    showMessage("已结赞过啦！");
                }

                break;

        }
    }

    private void zan() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("productId", id);
        AsynClient.post(MyHttpConfing.zan, this, params, new GsonHttpResponseHandler() {
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
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100) {
                    zanTag = 1;
                    iv_zan.setImageResource(R.mipmap.zan02);
                    zan_num.setText(dataBean.getLikeQuantity()+1+"");
                }
            }
        });
    }

}
