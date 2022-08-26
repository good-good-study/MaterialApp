package com.sxt.chat.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @NotNull
    public static Context getCtx() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}