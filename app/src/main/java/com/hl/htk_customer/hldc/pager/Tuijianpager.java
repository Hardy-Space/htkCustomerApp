package com.hl.htk_customer.hldc.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.adapter.LvAdapter;
import com.hl.htk_customer.hldc.base.BaseViewpager;
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.hldc.view.XListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/26.author
 */

public class Tuijianpager extends BaseViewpager implements XListView.IXListViewListener{
    private static final String TAG = Tuijianpager.class.getSimpleName();
    private XListView listView;
    private int page = 1;
    private View view;
    private LvAdapter adapter;
    private Context context;
    private int categoryId = 0;

    public Tuijianpager(Context context, int categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
    }

    @Override
    public View initView() {
        view=View.inflate(mactivity, R.layout.pager_layout,null);
        init(); //初始化
//        initData();
        return view;
    }


    private void init() {
        listView = (XListView) view.findViewById(R.id.lv_pager);
        listView.setXListViewListener(this);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
    }
    private void onLoad(XListView xListView) {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }


    @Override
    public void initData() {
        getGoodsList();
    }

    private void initAdapter(){
        //设置适配器
        if (adapter == null){
            adapter = new LvAdapter(context,myList);
            listView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh(XListView lxist) {
        //下拉刷新
        page = 1;
//        myList.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    @Override
    public void onLoadMore(XListView list) {
        //上拉加载
        page+=1;
        getMoreGoodsList();
    }

    private void getMoreGoodsList() {
        HttpHelper.getInstance().getGoodsListById(context,
                categoryId, page, new JsonHandler<String>() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                        Log.d(TAG, "onSuccess() responseString==>>>" + responseString);
                        convertStringToListByLoadMore(ToolUtils.getJsonParseResult(responseString));
                        onLoad(listView); //
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                        Log.d(TAG, "onFailure() ==>>>");
                        onLoad(listView); //
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }
                });
    }

    private void getGoodsList(){
        HttpHelper.getInstance().getGoodsListById(context,
                categoryId, page, new JsonHandler<String>() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                Log.d(TAG,"onSuccess() responseString==>>>"+responseString);
                convertStringToList(ToolUtils.getJsonParseResult(responseString));
                onLoad(listView); //
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d(TAG,"onFailure() ==>>>");
                onLoad(listView); //
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    List<GoodBean> myList = new ArrayList<>();
    private void convertStringToList(String result){
        myList.clear();
        JSONArray obj =  null;
        try{
            obj = new JSONArray(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(obj.length() > 0){
            for(int i=0;i<obj.length();i++){
                GoodBean bean = new GoodBean();
                try{
                    bean.setShopId( obj.getJSONObject(i).getInt("shopId"));
                    bean.setId( obj.getJSONObject(i).getInt("id"));
                    bean.setPrice( obj.getJSONObject(i).getDouble("price"));
                    bean.setImgUrl( obj.getJSONObject(i).getString("imgUrl"));
                    bean.setProductName( obj.getJSONObject(i).getString("productName"));
                    bean.setGrade( obj.getJSONObject(i).getInt("grade"));
                    bean.setMonthlySalesVolume( obj.getJSONObject(i).getInt("monthlySalesVolume"));
                    bean.setCollectState( obj.getJSONObject(i).getInt("collectState"));
                    bean.setProductDetail( obj.getJSONObject(i).getString("productDetail"));
                    bean.setDescription( obj.getJSONObject(i).getString("description"));
                    bean.setCategoryName(obj.getJSONObject(i).getString("categoryname"));
                }catch(Exception e){
                    e.printStackTrace();
                }
                myList.add(bean);
            }
            initAdapter();
        }
    }

    private void convertStringToListByLoadMore(String result){
        JSONArray obj =  null;
        try{
            obj = new JSONArray(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(obj.length() > 0){
            for(int i=0;i<obj.length();i++){
                GoodBean bean = new GoodBean();
                try{
                    bean.setShopId( obj.getJSONObject(i).getInt("shopId"));
                    bean.setId( obj.getJSONObject(i).getInt("id"));
                    bean.setPrice( obj.getJSONObject(i).getDouble("price"));
                    bean.setImgUrl( obj.getJSONObject(i).getString("imgUrl"));
                    bean.setProductName( obj.getJSONObject(i).getString("productName"));
                    bean.setGrade( obj.getJSONObject(i).getInt("grade"));
                    bean.setMonthlySalesVolume( obj.getJSONObject(i).getInt("monthlySalesVolume"));
                    bean.setCollectState( obj.getJSONObject(i).getInt("collectState"));
                    bean.setProductDetail( obj.getJSONObject(i).getString("productDetail"));
                    bean.setDescription( obj.getJSONObject(i).getString("description"));
                    bean.setCategoryName(obj.getJSONObject(i).getString("categoryname"));
                }catch(Exception e){
                    e.printStackTrace();
                }
                myList.add(bean);
            }
            initAdapter();
        }
    }

}
