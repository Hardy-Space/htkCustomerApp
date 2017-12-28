package com.hl.htk_customer.utils;

import android.graphics.Color;

/**
 * Created by Administrator on 2017/10/12.
 *
 */

public class ColorAlphaUtils {

    /** 根据百分比改变颜色透明度 */
    public static int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
