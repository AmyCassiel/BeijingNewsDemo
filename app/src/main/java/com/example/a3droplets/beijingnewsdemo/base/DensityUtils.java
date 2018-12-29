package com.example.a3droplets.beijingnewsdemo.base;

import android.content.Context;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description:
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率从dip的单位转成px(像素)*/
    public static int dip2px(Context context, float dpvalue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpvalue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从px(像素)的单位 转为 dp*/
    public static int px2dip(Context context, float pxvalue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxvalue / scale + 0.5f);
    }
}
