package com.sxt.chat.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

@SuppressLint("ServiceCast")
public class Utils {
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     */
    public static boolean isGpsOrNetWorkEnable(Context context) {
        android.location.LocationManager locationManager
                = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = false;
        if (locationManager != null) {
            gps = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        }
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = false;
        if (locationManager != null) {
            network = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
        }
        return gps || network;
    }

    /**
     * 判断Gps定位是否开启
     */
    public static boolean isGpsOpen(Context context) {
        android.location.LocationManager locationManager
                = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = false;
        if (locationManager != null) {
            gps = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        }
        return gps;
    }

    /**
     * 判断网络定位是否开启
     */
    public static boolean isGpsNetWorkOpen(Context context) {
        android.location.LocationManager locationManager
                = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = false;
        if (locationManager != null) {
            network = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
        }
        return network;
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context c) {
        Context context = c.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo aNetworkInfo : networkInfo) {
                    // 判断当前网络状态是否为连接状态
                    if (aNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断WIFI是否打开
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (mgrConn != null) {
            if (mgrTel != null) {
                return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                        .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                        .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
            }
        }
        return false;
    }

    /**
     * 判断是否是3G网络
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断是wifi还是3g网络
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}