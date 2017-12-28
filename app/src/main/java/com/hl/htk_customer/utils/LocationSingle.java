package com.hl.htk_customer.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hl.htk_customer.R;
import com.hl.htk_customer.model.LocationInfoManager;

/**
 * Created by Administrator on 2017/9/12.
 */

public class LocationSingle {

    private AMapLocationClient locationClientSingle = null;//单次定位
    private  TextView tvLocation;
    private  Context context;
    private AMapLocation mLocation;

    private LocationSingleListener mLocationSingleListener;

    public LocationSingle(TextView tv , Context context){
        this.tvLocation = tv;
        this.context = context;
        singleLocation();
    }

    public LocationSingle(Context context){
        this.context = context;
        singleLocation();
    }

    // 单次定位
    public void singleLocation() {
        startSingleLocation();
        if (tvLocation != null)
            tvLocation.setText(R.string.location_doing);
    }

    /**
     * 启动单次客户端定位
     */
    void startSingleLocation(){
        if(null == locationClientSingle){
            locationClientSingle = new AMapLocationClient(context);
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    /**
     * 停止单次客户端
     */
    public void stopSingleLocation(){
        if(null != locationClientSingle){
            locationClientSingle.stopLocation();
        }
    }

    /**
     * 销毁
     */
    public void destroySingleLocation(){
        if(null != locationClientSingle){
            locationClientSingle.onDestroy();
        }
    }


    /**
     * 单次客户端的定位监听
     */
    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            mLocation = location;
            if(null == location){
                if (tvLocation != null)
                    tvLocation.setText("定位失败");
            } else {

                //每次定位成功将定位信息保存
                LocationInfoManager.setLocation(location);

                //兴趣点（详细地址）
                String poiName = LocationUtils.getLocationPoiName(location);
                if (tvLocation != null){
                    tvLocation.setTag(poiName);
                    tvLocation.setText(LocationUtils.getLocationAddress(location));
                }

                //定位成功，接下来的操作方法
                mLocationSingleListener.success();
            }

        }
    };

    public AMapLocation getLocation() {
        return mLocation;
    }

    public void setLocationSingleListener(LocationSingleListener locationSingleListener) {
        this.mLocationSingleListener = locationSingleListener;
    }

    public interface LocationSingleListener{
        void success();
    }
}
