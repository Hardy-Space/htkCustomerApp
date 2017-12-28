package com.hl.htk_customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.FenLeiSpinnerAdapter;
import com.hl.htk_customer.adapter.LikeGridAdapter;
import com.hl.htk_customer.adapter.MySimpleAdapter;
import com.hl.htk_customer.adapter.RecommendShopAdapter;
import com.hl.htk_customer.adapter.RecommendsShopAdapter;
import com.hl.htk_customer.adapter.SearchShopAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.ShopAndRecommendEntity;
import com.hl.htk_customer.entity.ShopListEntity;
import com.hl.htk_customer.entity.StyleEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyGridView;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/21.
 * <p>
 * 店铺列表
 */

public class ShopListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.listView)
    MyListView listView;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.ll_style1)
    LinearLayout llStyle1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.ll_style2)
    LinearLayout llStyle2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.ll_style3)
    LinearLayout llStyle3;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.myGridView)
    MyGridView myGridView;
    @Bind(R.id.mark)
    TextView liekMark;
    @Bind(R.id.ll_mark)
    LinearLayout llMark;


    RecommendsShopAdapter searchShopAdapter;
    LikeGridAdapter likeGridAdapter;


    private int distanceType = 0;
    private int mark = 0; //0外卖  1团购
    private String name = "";
    private int categoryId;
    private int childCId = 0;
    private int page = 1;
    private ShopAndRecommendEntity shopListEntity;

    private StyleEntity styleEntity;

    FenLeiSpinnerAdapter fenLeiSpinnerAdapter;

    private PopupWindow popupWindow1;
    private PopupWindow popupWindow2;
    private View layout1;
    private View layout2;
    private ListView listView1;
    private ListView listView2;

    List<String> list = new ArrayList<>();

    private void initView() {

        layout1 = getLayoutInflater().inflate(R.layout.poupwindow, null);
        listView1 = (ListView) layout1.findViewById(R.id.listView);
        fenLeiSpinnerAdapter = new FenLeiSpinnerAdapter(this);
        listView1.setAdapter(fenLeiSpinnerAdapter);


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String categoryName = styleEntity.getData().get(position).getCategoryName();
                childCId = styleEntity.getData().get(position).getId();
                tv1.setText(categoryName);
                getShopListById(page);
                if (popupWindow1 != null && popupWindow1.isShowing()) {
                    popupWindow1.dismiss();
                }

            }
        });


        layout2 = getLayoutInflater().inflate(R.layout.popupwindow_distance, null);
        listView2 = (ListView) layout2.findViewById(R.id.listView);
        list.add("500m");
        list.add("1000m");
        list.add("2000m");
        list.add("3000m");
        list.add("不限");

        MySimpleAdapter simpleAdapter = new MySimpleAdapter(this);
        simpleAdapter.setData(list);
        listView2.setAdapter(simpleAdapter);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = list.get(position).toString();
                tv2.setText(text);

                if (text.equals("500m")) {
                    distanceType = 1;
                } else if (text.equals("1000m")) {
                    distanceType = 2;
                } else if (text.equals("2000m")) {
                    distanceType = 3;
                } else if (text.equals("3000m")) {
                    distanceType = 4;
                } else if (text.equals("不限")) {
                    distanceType = 0;
                }

                getShopListById(page);
                if (popupWindow2 != null && popupWindow2.isShowing()) {
                    popupWindow2.dismiss();
                }

            }
        });


    }


    private void showPopupWindow1(View parent, Context context) {

        if (popupWindow1 != null && popupWindow1.isShowing()) {
            popupWindow1.dismiss();
            return;
        }

        popupWindow1 = new PopupWindow(layout1, WindowManager.LayoutParams.MATCH_PARENT, MyUtils.Dp2Px(context, 200), true);
        popupWindow1.setContentView(layout1);
        popupWindow1.showAsDropDown(parent);

        popupWindow1.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果点击了popupwindow的外部，popupwindow也会消失
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    if (popupWindow1.isShowing()) {
                        popupWindow1.dismiss();
                    }

                    return true;
                }
                return false;
            }
        });
    }


    private void showPopupWindow2(View parent, Context context) {

        if (popupWindow2 != null && popupWindow2.isShowing()) {
            popupWindow2.dismiss();
            return;
        }

        popupWindow2 = new PopupWindow(layout2, parent.getWidth(), WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow2.setContentView(layout2);
        popupWindow2.showAsDropDown(parent);


        popupWindow2.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果点击了popupwindow的外部，popupwindow也会消失
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    if (popupWindow2.isShowing()) {
                        popupWindow2.dismiss();
                    }

                    return true;
                }
                return false;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_shop_list);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initWidget();

    }

    private void initWidget() {
        mark = getIntent().getIntExtra("mark", -1);
        categoryId = getIntent().getIntExtra("categoryId", -1);
        name = getIntent().getStringExtra("name");
        tvTitle.setText(name);
        llReturn.setOnClickListener(this);

        likeGridAdapter = new LikeGridAdapter(this);
        myGridView.setAdapter(likeGridAdapter);
        searchShopAdapter = new RecommendsShopAdapter(this);
        listView.setAdapter(searchShopAdapter);
        listView.setOnItemClickListener(this);
        myGridView.setOnItemClickListener(this);

        getStyle();
        getShopListById(page);
        llStyle1.setOnClickListener(this);
        llStyle2.setOnClickListener(this);
        llStyle3.setOnClickListener(this);

        scrollView.setMode(PullToRefreshBase.Mode.BOTH);

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                getShopListById(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if (shopListEntity != null && shopListEntity.getData().getShopList() != null && shopListEntity.getData().getShopList().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    page++;
                    getShopListById(page);
                }

            }
        });


        searchShopAdapter.setCollectionListener(new RecommendsShopAdapter.CollectionListener() {
            @Override
            public void collectionClick(int position, ImageView collection) {

                ShopAndRecommendEntity.DataBean.ShopListBean item = (ShopAndRecommendEntity.DataBean.ShopListBean) searchShopAdapter.getItem(position);

                int shopId = item.getShopId();
                boolean collection1 = item.isCollection();

                if (collection1) {
                    collection(shopId, 0, collection, position);
                } else {
                    collection(shopId, 1, collection, position);
                }

            }
        });


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                complete();
            }

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.ll_style1:
                showPopupWindow1(llStyle1, ShopListActivity.this);
                break;
            case R.id.ll_style2:
                showPopupWindow2(llStyle2, ShopListActivity.this);
                break;
            case R.id.ll_style3:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.listView:

                ShopAndRecommendEntity.DataBean.ShopListBean item = (ShopAndRecommendEntity.DataBean.ShopListBean) searchShopAdapter.getItem(position);

                if (mark == 0) {
                    Intent intent = new Intent(ShopListActivity.this, WmShopDetailActivity.class);
                    intent.putExtra("shopId", item.getShopId());
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(ShopListActivity.this, TuanGouShopActivity.class);
                    intent.putExtra("shopId", item.getShopId());
                    startActivity(intent);
                }


                break;
            case R.id.myGridView:


                ShopAndRecommendEntity.DataBean.RecommendShopListBean recommendShopListBean = shopListEntity.getData().getRecommendShopList().get(position);

                if (mark == 0) {
                    Intent intent = new Intent(ShopListActivity.this, WmShopDetailActivity.class);
                    intent.putExtra("shopId", recommendShopListBean.getShopId());
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(ShopListActivity.this, TuanGouShopActivity.class);
                    intent.putExtra("shopId", recommendShopListBean.getShopId());
                    startActivity(intent);
                }


                break;


        }


    }


    private void getStyle() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", categoryId);
        params.put("mark", mark); //0 外卖  1 团购
        AsynClient.post(MyHttpConfing.shopStyle, this, params, new GsonHttpResponseHandler() {
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
                styleEntity = gson.fromJson(rawJsonResponse, StyleEntity.class);


                if (styleEntity.getCode() == 100) {

                    if (styleEntity.getData() == null) return;
                    fenLeiSpinnerAdapter.setData(styleEntity.getData());

                }

            }
        });

    }



    /*
    * 根据分类获取店铺列表
    *
    * 外卖还是团购
    * 小分类
    * 距离
    * */

    private void getShopListById(final int page) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", categoryId);
        //    params.put("mark", 1);
        params.put("mark", mark); //0 外卖  1 团购
        params.put("pageNumber", page);
        params.put("childCId", childCId);
        try {
            params.put("userLo", Double.parseDouble(app.getLoginState().getLongitude()));
            params.put("userLa", Double.parseDouble(app.getLoginState().getLatitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("distanceType", distanceType);


        AsynClient.post(MyHttpConfing.shopListById, this, params, new GsonHttpResponseHandler() {
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
                shopListEntity = gson.fromJson(rawJsonResponse, ShopAndRecommendEntity.class);

                if (shopListEntity.getCode() == 100) {

                    //     if (shopListEntity.getData() == null) return;

                    if (shopListEntity.getData() == null) {

                        if (page == 1) {

                            List<ShopAndRecommendEntity.DataBean.ShopListBean> list = new ArrayList<ShopAndRecommendEntity.DataBean.ShopListBean>();
                            searchShopAdapter.setData(list);
                            showMessage(shopListEntity.getMessage());


                            if (shopListEntity.getData() != null && shopListEntity.getData().getRecommendShopList() != null) {

                                llMark.setVisibility(View.VISIBLE);
                                likeGridAdapter.setData(shopListEntity.getData().getRecommendShopList());

                            } else {
                                llMark.setVisibility(View.GONE);

                                List<ShopAndRecommendEntity.DataBean.RecommendShopListBean> listBeen = new ArrayList<ShopAndRecommendEntity.DataBean.RecommendShopListBean>();
                                likeGridAdapter.setData(listBeen);

                            }


                        } else {

                        }
                        complete();
                        return;

                    }


                    if (page == 1) {
                        searchShopAdapter.setData(shopListEntity.getData().getShopList());

                        if (shopListEntity.getData().getRecommendShopList() != null) {

                            llMark.setVisibility(View.VISIBLE);
                            likeGridAdapter.setData(shopListEntity.getData().getRecommendShopList());

                        } else {
                            llMark.setVisibility(View.GONE);

                            List<ShopAndRecommendEntity.DataBean.RecommendShopListBean> listBeen = new ArrayList<ShopAndRecommendEntity.DataBean.RecommendShopListBean>();
                            likeGridAdapter.setData(listBeen);

                        }


                    } else {
                        searchShopAdapter.addData(shopListEntity.getData().getShopList());
                    }

                }


                complete();
            }
        });

    }


    private void complete() {
        if (scrollView != null) {
            scrollView.onRefreshComplete();
        }

    }


    private void collection(int id, final int mark, final ImageView imageView, final int position) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", id);
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.collection, this, params, new GsonHttpResponseHandler() {
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
                    ShopAndRecommendEntity.DataBean.ShopListBean item = (ShopAndRecommendEntity.DataBean.ShopListBean) searchShopAdapter.getItem(position);
                    if (mark == 1) {
                        imageView.setImageResource(R.mipmap.collection2);
                        item.setCollection(true);
                    } else {
                        imageView.setImageResource(R.mipmap.collection1);
                        item.setCollection(false);


                    }

                    page = 1;
                    getShopListById(page);


                } else {

                }

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (popupWindow1 != null && popupWindow1.isShowing()) {
                popupWindow1.dismiss();

            } else if (popupWindow2 != null && popupWindow2.isShowing()) {
                popupWindow2.dismiss();

            } else {
                finish();

            }


            return false;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow1 != null){
            popupWindow1.dismiss();
        }

        if (popupWindow2 != null){
            popupWindow2.dismiss();
        }
    }
}
