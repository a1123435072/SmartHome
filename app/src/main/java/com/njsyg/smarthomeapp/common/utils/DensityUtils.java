package com.njsyg.smarthomeapp.common.utils;

import android.content.Context;

/**
 * Created by zz on 2016/9/1.
 */
public class DensityUtils {
    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp){
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int)(dp*density+0.5f);//4.9->5  4.4->5防止四舍五入
        return px;
    }

    /**
     * px转dp dp = px/设备密度
     */
    public static float px2dp(Context context, float px){
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px/density;
        return dp;
    }
}
