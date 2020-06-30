package com.sxt.chat.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;

/**
 * Created by xt.sun
 * 2019-10-09
 * 通过高德地图的定位功能发起定位 ，最后返回坐标
 */
@SuppressLint("StaticFieldLeak")
public class LocationManager extends ContextWrapper implements AMapLocationListener {
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private String TAG = this.getClass().getName();
    private OnLocationListener onLocationListener;
    private LocationReceiver locationReceiver;
    private static LocationManager locationManager;
    private final String ACTION_GPS_STATE = "android.location.PROVIDERS_CHANGED";

    private LocationManager(Context ctx) {
        super(ctx);
        initLocationOption();
        registerReceiver();
    }

    public static LocationManager getInstance(Context context) {
        if (locationManager == null) {
            locationManager = new LocationManager(context);
        }
        return locationManager;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                stopLocation();
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                Log.e(TAG, String.format("定位成功 ： %s", new Gson().toJson(latLng)));
                if (onLocationListener != null) {
                    onLocationListener.onLocation(aMapLocation);
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 初始化定位参数
     */
    private void initLocationOption() {
        AMapLocationClientOption.AMapLocationMode mode;
        if (Utils.isGpsOpen(this)) {
            mode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;//高精度模式。
            Log.e(TAG, "当前定位模式为 --->>> 高精度模式");
        } else if (Utils.isGpsNetWorkOpen(this)) {
            mode = AMapLocationClientOption.AMapLocationMode.Battery_Saving;//低功耗模式。
            Log.e(TAG, "当前定位模式为 --->>> 低功耗模式");
        } else {
            mode = AMapLocationClientOption.AMapLocationMode.Device_Sensors;//仅限设备模式。
            Log.e(TAG, "当前定位模式为 --->>> 仅限设备模式");
        }
        if (mLocationOption == null) {
            mLocationOption = new AMapLocationClientOption()
                    .setLocationMode(mode)
                    .setNeedAddress(true)//设置是否返回地址信息（默认返回地址信息）
                    .setInterval(2000)//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
                    .setHttpTimeOut(8000)//单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
                    .setLocationCacheEnable(true);//缓存机制默认开启，可以通过以下接口进行关闭。
            // 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
            //.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        } else {
            mLocationOption.setLocationMode(mode);
        }
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);//初始化定位
            mLocationClient.setLocationListener(this);//设置定位回调监听
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();//设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        } else {
            mLocationClient.setLocationOption(mLocationOption);
        }
        startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    /**
     * 发起定位
     */
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    /**
     * 当不需要使用定位的时候，记得释放资源
     */
    public void releaseClient() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            mLocationClient = null;
            locationManager = null;
            onLocationListener = null;
        }
        unregisterReceiver();
    }

    /**
     * 在释放资源时，同时取消注册定位模式广播
     */
    private void unregisterReceiver() {
        try {
            if (locationReceiver != null) {
                unregisterReceiver(locationReceiver);
                locationReceiver = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册定位模式广播
     */
    private void registerReceiver() {
        unregisterReceiver();
        if (locationReceiver == null) {
            locationReceiver = new LocationReceiver();
            registerReceiver(locationReceiver, new IntentFilter(ACTION_GPS_STATE));
        }
    }

    /**
     * 获取定位之后，自动停止定位
     */
    public LocationManager setOnLocationListener(OnLocationListener onLocationListener) {
        if (this.onLocationListener != null) {
            stopLocation();
            this.onLocationListener = null;
        }
        this.onLocationListener = onLocationListener;
        return this;
    }

    public interface OnLocationListener {
        void onLocation(AMapLocation aMapLocation);
    }

    /**
     * 监听到定位模式发生变化的广播
     */
    private class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_GPS_STATE.equals(intent.getAction())) {
                Log.e(TAG, "监听到定位模式发生变化");
                initLocationOption();
            }
        }
    }
}