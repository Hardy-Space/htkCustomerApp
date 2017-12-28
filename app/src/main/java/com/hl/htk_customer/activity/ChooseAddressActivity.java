package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.HomeAddressAdapter;
import com.hl.htk_customer.adapter.NearAddressAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AddressListEntity;
import com.hl.htk_customer.model.EventAddressEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.LocationSearchUtils;
import com.hl.htk_customer.utils.LocationSingle;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * <p>
 * 选择收货地址
 */

public class ChooseAddressActivity extends BaseActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener
                    , LocationSingle.LocationSingleListener , PoiSearch.OnPoiSearchListener{

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_getAddress)
    TextView tvGetAddress;
    @Bind(R.id.listView_myAddress)
    MyListView listViewMyAddress;
    @Bind(R.id.listView_nearAddress)
    MyListView listViewNearAddress;
    @Bind(R.id.tv_more_address)
    TextView tvMoreAddress;
    @Bind(R.id.details)
    TextView mDetails;

    private final int REQUEST_CODE = 100;

    private HomeAddressAdapter homeAddressAdapter;
    private NearAddressAdapter nearAddressAdapter;

    private LocationSingle mLocationSingle;
    private AMapLocation mLocation;
    private PoiSearch.Query mQuery;
    private LocationSearchUtils locationSearchUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_address);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {

        tvTitle.setText(getResources().getText(R.string.choose_address));
        tvRight.setText(getResources().getText(R.string.add_address));
        tvLocation.setText(app.getLoginState().getAddress());
        llReturn.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        tvGetAddress.setOnClickListener(this);
        tvMoreAddress.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        listViewMyAddress.setOnItemClickListener(this);
        listViewNearAddress.setOnItemClickListener(this);

        homeAddressAdapter = new HomeAddressAdapter(this, 0);
        listViewMyAddress.setAdapter(homeAddressAdapter);
        nearAddressAdapter = new NearAddressAdapter(this);
        nearAddressAdapter.setTvTitleSubVisibility(false);
        listViewNearAddress.setAdapter(nearAddressAdapter);
        getAddressList();

        mLocationSingle = new LocationSingle(tvLocation, this);
        mLocationSingle.setLocationSingleListener(this);

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
            case R.id.tv_right:
                //添加收货地址
                startActivity(new Intent(ChooseAddressActivity.this, AddAddressActivity.class));
                break;
            case R.id.tv_getAddress:
                new LocationSingle(tvLocation , ChooseAddressActivity.this);
                break;
            case R.id.tv_more_address:
                startActivityForResult(new Intent(ChooseAddressActivity.this, AllLocationActivity.class) , REQUEST_CODE);
                break;
            case R.id.tv_location:
                EventBus.getDefault().post(new EventAddressEntity(tvLocation.getTag().toString()));
                finish();
                break;
            default:
                break;

        }

    }


    //获取地址列表
    private void getAddressList() {

        RequestParams params = AsynClient.getRequestParams();


        AsynClient.post(MyHttpConfing.homeAddress, this, params, new GsonHttpResponseHandler() {
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
                AddressListEntity addressListEntity = gson.fromJson(rawJsonResponse, AddressListEntity.class);
                if (addressListEntity.getCode() == 100) {
                    homeAddressAdapter.setData(addressListEntity.getData());
                }

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.listView_myAddress:

                AddressListEntity.DataBean item = (AddressListEntity.DataBean) homeAddressAdapter.getItem(position);

                app.getDefaultAddress().setUserName(item.getUserName());
                app.getDefaultAddress().setSex(item.getSex());
                app.getDefaultAddress().setLocation(item.getLocation());
                app.getDefaultAddress().setAddress(item.getAddress());
                app.getDefaultAddress().setPhoneNumber(item.getPhone());
                EventBus.getDefault().post(new EventAddressEntity(item.getAddress()));
                finish();


                break;
            case R.id.listView_nearAddress:

                PoiItem item1 = (PoiItem) nearAddressAdapter.getItem(position);
                EventBus.getDefault().post(new EventAddressEntity(item1.getTitle()));
                finish();

                break;
            default:
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 101)
            finish();
    }

    /**
     * 定位成功，搜索附近兴趣点
     */
    @Override
    public void success() {
        mLocation = mLocationSingle.getLocation();
        locationSearchUtils = new LocationSearchUtils(this, mLocation);
        locationSearchUtils.setSearchType("120000");
        locationSearchUtils.setPageSize(3);
        locationSearchUtils.setOnPoiSearchListener(this);
        locationSearchUtils.doSearchQuery();
        mQuery = locationSearchUtils.getQuery();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(mQuery)) {
                    List<PoiItem> poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        hideLoadingDialog();
                        nearAddressAdapter.setData(poiItems);
                    } else {
                        Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
