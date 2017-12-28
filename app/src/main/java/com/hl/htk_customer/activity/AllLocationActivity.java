package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.NearAddressAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.model.EventAddressEntity;
import com.hl.htk_customer.utils.LocationSearchUtils;
import com.hl.htk_customer.utils.LocationSingle;
import com.hl.htk_customer.widget.SegmentedGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/23.
 */

public class AllLocationActivity extends BaseActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener  , LocationSingle.LocationSingleListener
        , PoiSearch.OnPoiSearchListener , RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.rb_work)
    RadioButton rbWork;
    @Bind(R.id.rb_school)
    RadioButton rbSchool;
    @Bind(R.id.rb_live)
    RadioButton rbLive;
    @Bind(R.id.segmented_group_location)
    SegmentedGroup sgType;
    @Bind(R.id.listView)
    ListView listView;

    private static final String TAG = "AllLocationActivity";

    private NearAddressAdapter nearAddressAdapter;
    private AMapLocation mLocation;
    private LocationSingle mLocationSingle;
    private PoiSearch.Query mQuery;

    private ArrayList<String> items = new ArrayList<>() ;
    private LocationSearchUtils locationSearchUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_location);
        ButterKnife.bind(this);
        initWidget();
    }


    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.more_location));
        llReturn.setOnClickListener(this);

        items.add("120201");
        items.add("120300");
        items.add("141200");


        //加载提示
        showLoadingDialog();
        //开启定位
        mLocationSingle = new LocationSingle(this);
        mLocationSingle.setLocationSingleListener(this);


        nearAddressAdapter = new NearAddressAdapter(this);
        listView.setAdapter(nearAddressAdapter);
        listView.setOnItemClickListener(this);

        sgType.check(R.id.rb_work);
        sgType.setOnCheckedChangeListener(this);

    }

    @Override
    public void success() {
        mLocation = mLocationSingle.getLocation();
        locationSearchUtils = new LocationSearchUtils(this, mLocation);
        locationSearchUtils.setSearchType(items.get(0));
        locationSearchUtils.setOnPoiSearchListener(this);
        locationSearchUtils.doSearchQuery();
        mQuery = locationSearchUtils.getQuery();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationSingle != null)
            mLocationSingle.stopSingleLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationSingle != null)
            mLocationSingle.destroySingleLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PoiItem item1 = (PoiItem) nearAddressAdapter.getItem(position);
        EventBus.getDefault().post(new EventAddressEntity(item1.getTitle()));
        setResult(101 , new Intent().putExtra( "address" , item1.getTitle()));
        finish();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(mQuery)) {
                    List<PoiItem> poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        nearAddressAdapter.setData(poiItems);
                    } else {
                        Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
        hideLoadingDialog();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_work:
                locationSearchUtils.setSearchType(items.get(0));
                break;
            case R.id.rb_live:
                locationSearchUtils.setSearchType(items.get(1));
                break;
            case R.id.rb_school:
                locationSearchUtils.setSearchType(items.get(2));
                break;
        }
        group.check(checkedId);
        locationSearchUtils.doSearchQuery();
        mQuery = locationSearchUtils.getQuery();
    }
}
