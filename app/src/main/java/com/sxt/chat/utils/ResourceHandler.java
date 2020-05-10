package com.sxt.chat.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.sxt.chat.App;

/**
 * Created by xt.sun
 * 2020/5/10
 */
public class ResourceHandler {
    public static int parseColor(int resourceId) {
        return ContextCompat.getColor(App.getCtx(), resourceId);
    }

    public static Drawable parseDrawable(int resourceId) {
        return ContextCompat.getDrawable(App.getCtx(), resourceId);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = App.getCtx().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getCtx().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
