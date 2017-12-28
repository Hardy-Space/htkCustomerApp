package com.hl.htk_customer.fragment.waimai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.CollectionListActivity;
import com.hl.htk_customer.activity.ShopListActivity;
import com.hl.htk_customer.activity.diancan.ChoosePayStyleActivity;
import com.hl.htk_customer.adapter.ProductStyleAdapter;
import com.hl.htk_customer.assistant.OnRefreshListener;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.ProductEntity;
import com.hl.htk_customer.fragment.home.TuanGouFragment;
import com.hl.htk_customer.fragment.home.WaiMaiFragment;
import com.hl.htk_customer.greendao.FunctionTypeEntity;
import com.hl.htk_customer.model.EventAddressEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyGridView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import greendao.GreenDaoManager;

/**
 * Created by Administrator on 2017/6/19.
 */

public class ProductFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "ProductFragment";

    private View view = null;

    private MyGridView myGridView;

    ProductStyleAdapter productStyleAdapter;
    private ProductEntity productEntity;

    private int page = 1;
    private int mark = 0; //1团购  0外卖

    public static ProductFragment newInstance(int page, int mark) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putInt("mark", mark);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_product, null);
            page = getArguments().getInt("page");
            mark = getArguments().getInt("mark");
            myGridView = view.findViewById(R.id.myGridView);
            productStyleAdapter = new ProductStyleAdapter(getContext());
            myGridView.setAdapter(productStyleAdapter);
            myGridView.setOnItemClickListener(this);

            getProducts();
        }
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductEntity.DataBean item = (ProductEntity.DataBean) productStyleAdapter.getItem(position);
        if (new UserInfoManager(mContext).getISLOGIN()){
            Intent intent = new Intent(getActivity(), CollectionListActivity.class);
            intent.putExtra("categoryId", item.getId());
            intent.putExtra("name", item.getCategoryName());
            intent.putExtra("mark", mark);
            startActivity(intent);
        }else {
            showMessage(getString(R.string.login_please));
        }

    }

    //获取产品分类
    public void getProducts() {

        Log.i(TAG, mark + "getProducts: " + page);

        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", page);
        params.put("mark", mark);

        AsynClient.post(MyHttpConfing.productUrl
                , getContext(), params, new GsonHttpResponseHandler() {
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

                        Log.i(TAG, rawJsonResponse);

                        Gson gson = new Gson();
                        productEntity = gson.fromJson(rawJsonResponse, ProductEntity.class);

                        if (productEntity.getCode() == 100) {
                            productStyleAdapter.setData(productEntity.getData());

                        } else {
                            showMessage(productEntity.getMessage());
                        }



                    }
                });
    }

    @Override
    public void lazyInitData() {

    }
}
