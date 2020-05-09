package com.sxt.chat.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * release 版本将标记设置为false
 * <p>
 * 生产环境需要将标记全部置为false,防止请求数据结构泄露
 */
public class Config {
    public static final boolean isServerDebug = true;
    public static final boolean isLogDebug = true;

    public static String getId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System._ID);
    }

    /**
     * 获取app的版本号
     */
    public static String getAppVersion(Context context) {
        String version = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}