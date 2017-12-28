package com.hl.htk_customer.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.hl.htk_customer.model.CErrorMsg;
import com.hl.htk_customer.model.CommonMsg;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by w.chen on 2014/9/28.
 */
public class UiFormat {

    public static String getDisplayDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getFormatPlate(String plate) {
        String formattedPlate;
        if (plate.length() == 7) {
            formattedPlate = plate.substring(0, 2) + "·" + plate.substring(2);
        } else {
            formattedPlate = plate;
        }
        return formattedPlate;
    }

    public static String getTemperature(String format, int lower, int top) {
        return String.format(format, lower, top);
    }

    public static String getErrorMsg(String rawJsonData) {
        try {
            Gson gson = new Gson();
            CErrorMsg error = (CErrorMsg) gson.fromJson(rawJsonData, CErrorMsg.class);
            if (error != null && error.message != null)
                return error.message;
        } catch (Exception e) {
            // do nothing
        }
        return null;
    }


    public static CommonMsg getCommonMsg(String rawJsonResponse) {
        Gson gson = new Gson();
        CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
        return commonMsg;
    }


    public static void tryRequest(String request) {

        try {
            Log.i("rawJsonResponse-->", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
