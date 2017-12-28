package com.hl.htk_customer.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;



/**
 * Created by Administrator on 2017/9/14.
 */

public class LocationSearchUtils   {

    private static final String TAG = "LocationSearchUtils";

    private Context context;
    private AMapLocation location;

    private int currentPage = 0;// 当前页面，从0开始计数
    private int pageSize = 20;
    private PoiSearch.Query query;// Poi查询条件类
    private String searchKey = "";
    private String searchType = "";

    private LatLonPoint searchLatlonPoint;
    private PoiSearch poiSearch;
    private PoiSearch.OnPoiSearchListener onPoiSearchListener;


    public LocationSearchUtils(Context context , AMapLocation location){
        this.context = context;
        this.location = location;
        if (location != null)
            searchLatlonPoint = new LatLonPoint(location.getLatitude() , location.getLongitude());
    }

    public void setOnPoiSearchListener(PoiSearch.OnPoiSearchListener onPoiSearchListener) {
        this.onPoiSearchListener = onPoiSearchListener;
    }

    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    public void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(searchKey, searchType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(pageSize);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(context, query);
            poiSearch.setOnPoiSearchListener(onPoiSearchListener);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 500, true));//
            poiSearch.searchPOIAsyn();
            Log.i(TAG, "doSearchQuery: " + query);
        }
    }

    public PoiSearch.Query getQuery() {
        return query;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
