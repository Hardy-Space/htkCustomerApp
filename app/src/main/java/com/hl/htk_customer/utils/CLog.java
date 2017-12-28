package com.hl.htk_customer.utils;

import android.util.Log;

/**
 * Created by w.chen on 2014/10/17.
 */
public class CLog {
    private static boolean debuggable = true;

    public static void setDebuggable(boolean enable) {
        debuggable = enable;
    }

    public static void i(String tag, String log) {
        if (debuggable)
            Log.i(tag, log);
    }

    public static void d(String tag, String log) {
        if (debuggable)
            Log.d(tag, log);
    }

    public static void w(String tag, String log) {
        if (debuggable)
            Log.w(tag, log);
    }

    public static void e(String tag, String log) {
        if (debuggable)
            Log.e(tag, log);
    }
}
