package com.sxt.chat.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.umeng.commonsdk.UMConfigure;

import org.jetbrains.annotations.NotNull;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

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
        initUMSdk();
        initBmob();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 友盟统计
     * <p>
     * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
     * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
     * UMConfigure.init调用中appkey和channel参数请置为null）。
     */
    private void initUMSdk() {
        UMConfigure.init(this, "5eb69688167edd3bbc000126", "google", 0, null);
    }

    /**
     * 提供以下两种方式进行初始化操作
     */
    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("e83721273ee157be97206961952b99aa")
                .setConnectTimeout(5 * 1000L)
                .build();
        Bmob.initialize(config);
    }
}