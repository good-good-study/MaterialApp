package com.sxt.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class BasePrefs {

    private static PackageInfo packageInfo;

    protected SharedPreferences settings;

    public BasePrefs(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        return settings.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return settings.getString(key, defValue);
    }

    public boolean getAsBoolean(String key, boolean defValue) {
        String value = settings.getString(key, null);
        if (value == null) {
            return defValue;
        }
        return Boolean.parseBoolean(value);
    }

    public int getAsInt(String key, int defValue) {
        String value = settings.getString(key, null);
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Integer.parseInt(value);
    }

    public long getAsLong(String key, long defValue) {
        String value = settings.getString(key, null);
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Long.parseLong(value);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return settings.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public long getLong(String key, long defValue) {
        return settings.getLong(key, defValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defValue) {
        return settings.getFloat(key, defValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionName;
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionCode;
        }
        return 0;
    }

    private static synchronized PackageInfo getPackageInfo(Context context) {
        if (packageInfo == null) {
            try {
                String packageName = context.getPackageName();
                PackageManager pm = context.getPackageManager();
                packageInfo = pm.getPackageInfo(packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return packageInfo;
    }
}