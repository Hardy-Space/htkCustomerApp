package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.SearchShopAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.ShopListEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * <p>
 * s搜索
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.listView)
    PullToRefreshListView listView;
    @Bind(R.id.empty_view)
    ImageView emptyView;

    SearchShopAdapter searchShopAdapter;
    private String keyWord = "";
    private int mark = 0; //标识（外卖是0 团购是1）
    private int page = 1;
    private ShopListEntity shopListEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        mark = getIntent().getIntExtra("mark", 0);

        llReturn.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        listView.setOnItemClickListener(this);
        searchShopAdapter = new SearchShopAdapter(this);
        listView.setAdapter(searchShopAdapter);
        listView.setEmptyView(emptyView);


        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                searchShop(keyWord, mark, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                if (shopListEntity != null && shopListEntity.getData() != null && shopListEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    page++;
                    searchShop(keyWord, mark, page);
                }


            }
        });


        searchShopAdapter.setCollectionListener(new SearchShopAdapter.CollectionListener() {
            @Override
            public void collectionClick(int position, ImageView collection) {
                ShopListEntity.DataBean item = (ShopListEntity.DataBean) searchShopAdapter.getItem(position);

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
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        keyWord = etSearch.getText().toString().trim();

        //  if (TextUtils.isEmpty(keyWord)) return;

        searchShop(keyWord, mark, page);
    }


    private void searchShop(String keyWord, int mark, final int page) {

        // String keyWord  搜索条件(字符串)   int mark 标识（外卖是0 团购是1） int pageNumber页数

        RequestParams params = AsynClient.getRequestParams();
        params.put("keyWord", keyWord);
        params.put("mark", mark);
        params.put("pageNumber", page);

        AsynClient.post(MyHttpConfing.searchShop, this, params, new GsonHttpResponseHandler() {
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
                shopListEntity = gson.fromJson(rawJsonResponse, ShopListEntity.class);

                if (shopListEntity.getCode() == 100) {

                    if (shopListEntity.getData() == null) {

                        if (page == 1) {
                            List<ShopListEntity.DataBean> list = new ArrayList<ShopListEntity.DataBean>();
                            searchShopAdapter.setData(list);
                        } else {

                        }
                        complete();
                        return;

                    }

                    if (page == 1) {
                        searchShopAdapter.setData(shopListEntity.getData());
                    } else {
                        searchShopAdapter.addData(shopListEntity.getData());
                    }


                } else {
                    //   List<ShopListEntity.FunctionType> list  = new ArrayList<ShopListEntity.FunctionType>();
                    //    searchShopAdapter.setData(list);
                    showMessage(shopListEntity.getMessage());
                }


                complete();

            }
        });

    }


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ShopListEntity.DataBean item = (ShopListEntity.DataBean) searchShopAdapter.getItem(position - 1);

        if (!new UserInfoManager(this).getISLOGIN()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else {
            switch (mark){
                case 0:
                    //外卖
                    Intent intent = new Intent(mContext, WmShopDetailActivity.class);
                    intent.putExtra("shopId", item.getShopId());
                    startActivity(intent);
                    break;
                case 1:
                    //团购
                    Intent intent2 = new Intent(mContext, TuanGouShopActivity.class);
                    intent2.putExtra("shopId", item.getShopId());
                    startActivity(intent2);
                    break;

            }

        }



/*        if (mark == 0) {
            Intent intent = new Intent(SearchActivity.this, WmShopDetailActivity.class);
            intent.putExtra("shopId", item.getShopId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(SearchActivity.this, TuanGouShopActivity.class);
            intent.putExtra("shopId", item.getShopId());
            startActivity(intent);
        }*/


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
                    ShopListEntity.DataBean item = (ShopListEntity.DataBean) searchShopAdapter.getItem(position);
                    if (mark == 1) {
                        imageView.setImageResource(R.mipmap.collection2);
                        item.setCollection(true);
                    } else {
                        imageView.setImageResource(R.mipmap.collection1);
                        item.setCollection(false);
                    }

                } else {

                }

            }
        });

    }


}
