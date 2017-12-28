package com.hl.htk_customer.model;

import com.amap.api.location.AMapLocation;
import com.hl.htk_customer.utils.MyApplication;

/**
 * Created by Administrator on 2017/9/20.
 */

public class LocationInfoManager {

    public static AMapLocation getLocation(){
        return MyApplication.getmAMapLocation();
    }

    public static void setLocation(AMapLocation location){
        MyApplication.setmAMapLocation(location);
    }

}
