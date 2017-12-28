package com.hl.htk_customer.activity.buffetmeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class BuffetMealFragment extends BaseFragment {

    private static final String SHOPID = "SHOPID";

    @Bind(R.id.rv_fragment_buffet_meal)
    RecyclerView rvFragmentBuffetMeal;
    @Bind(R.id.refresh_fragment_buffet_meal)
    SmartRefreshLayout refreshFragmentBuffetMeal;

    private BuffetMealGoodsAdapter mBuffetMealGoodsAdapter;
    private int shopID;

    public static BuffetMealFragment newInstance(Integer shopID) {

        Bundle args = new Bundle();
        args.putInt(SHOPID, shopID);
        BuffetMealFragment fragment = new BuffetMealFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buffet_meal, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        shopID = arguments.getInt(SHOPID);

        initRv();
        initRefresh();

        return view;
    }

    private void initRv() {
        rvFragmentBuffetMeal.setLayoutManager(new GridLayoutManager(mContext , 2));
        mBuffetMealGoodsAdapter = new BuffetMealGoodsAdapter(R.layout.item_buffet_meal_goods , null , mContext);
        rvFragmentBuffetMeal.setAdapter(mBuffetMealGoodsAdapter);

        mBuffetMealGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView tvChooseNum = (TextView) mBuffetMealGoodsAdapter.getViewByPosition(rvFragmentBuffetMeal, position, R.id.tv_buffet_meal_goods_choose_num);
                int chooseNum = Integer.valueOf(tvChooseNum.getText().toString());
                BuffetMealGoodsDetailsActivity.launch(getActivity() , mBuffetMealGoodsAdapter.getData().get(position).getId() , chooseNum);
            }
        });

        mBuffetMealGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView tvChooseNum = (TextView) mBuffetMealGoodsAdapter.getViewByPosition(rvFragmentBuffetMeal, position, R.id.tv_buffet_meal_goods_choose_num);
                Integer num = Integer.valueOf(tvChooseNum.getText().toString());
                switch (view.getId()){
                    case R.id.tv_buffet_meal_goods_cut:
                        if (num <= 0) return;
                        num--;
                        tvChooseNum.setText(String.valueOf(num));
                        break;
                    case R.id.tv_buffet_meal_goods_add:
                        if (num >= 50) return;
                        num++;
                        tvChooseNum.setText(String.valueOf(num));
                        break;
                }
            }
        });

        getGoodsData();
    }

    private void initRefresh() {
        refreshFragmentBuffetMeal.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getGoodsData();
            }
        });
    }

    @Override
    public void lazyInitData() {

    }

    public void getGoodsData(){
        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", shopID);
        AsynClient.post(MyHttpConfing.diancanFenleiList, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                refreshFragmentBuffetMeal.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                refreshFragmentBuffetMeal.finishRefresh();
                Gson gson = new Gson();
                BuffetMealGoodsInfo buffetMealGoodsInfo = gson.fromJson(rawJsonResponse, BuffetMealGoodsInfo.class);

                if (buffetMealGoodsInfo.getCode() == 100) {
                    mBuffetMealGoodsAdapter.setNewData(buffetMealGoodsInfo.getData());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
