package com.hl.htk_customer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SpUtils {

    private final static String SHAREDPREFERENCES_NAME = "sp_data";

    public static void put(Context context , String key , Object object){

        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String)
            editor.putString(key , (String) object);
        else if (object instanceof Long)
            editor.putLong(key , (Long) object);
        else if (object instanceof Integer)
            editor.putInt(key , (Integer) object);
        else if (object instanceof Boolean)
            editor.putBoolean(key , (Boolean) object);
        else if (object instanceof Float)
            editor.putFloat(key , (Float) object);

        editor.apply();
    }

    public static Object get(Context context , String key , Object defaultObject){
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String)
            return sp.getString(key , (String) defaultObject);
        else if (defaultObject instanceof Long)
            return sp.getLong(key , (Long) defaultObject);
        else if (defaultObject instanceof Integer)
            return sp.getInt(key , (Integer) defaultObject);
        else if (defaultObject instanceof Boolean)
            return sp.getBoolean(key , (Boolean) defaultObject);
        else if (defaultObject instanceof Float)
            return sp.getFloat(key , (Float) defaultObject);

        return null;
    }

    public static void clear(Context context){
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
