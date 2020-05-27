package com.sxt.chat.utils;

import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

/**
 * Created by xt.sun
 * 2019-08-23
 * 沉浸式状态栏样式
 */
public class SystemUiStyle {

    /**
     * 将内容提升至状态栏
     */
    public static void fitSystemWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 设置导航栏颜色
     */
    public static void setNavigationBarColor(Activity activity, int colorResId) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, colorResId));
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int colorResId) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
        }
    }

    /**
     * 设置状态栏Light主题
     */
    public static void setLightStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置状态栏Dark主题
     */
    public static void setDarkStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * Android官方标准的系统UI全屏样式
     * 重写Activity的onWindowFocusChanged方法
     * 在获取到焦点时调用
     * if (hasFocus) initWindowStyle();
     */
    public static void fullScreenStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE//防止系统栏隐藏时内容区域大小发生变化
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//隐藏导航栏
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏底部的 三个 虚拟按键导航栏
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }
}