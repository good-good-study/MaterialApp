package com.sxt.chat.utils;

import android.util.Log;

public class LogUtil {
    static String TAG = "Material";

    public static void i(String tag, String message) {
        if (Config.isLogDebug) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (Config.isLogDebug) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (Config.isLogDebug) {
            Log.e(tag, message);
        }
    }
}