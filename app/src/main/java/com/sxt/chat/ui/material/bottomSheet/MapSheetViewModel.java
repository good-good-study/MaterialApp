package com.sxt.chat.ui.material.bottomSheet;

import android.app.Application;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.sxt.chat.R;
import com.sxt.chat.utils.MathTool;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.mvvm.model.RespLocation;
import com.sxt.mvvm.model.location.LocationInfo;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class MapSheetViewModel extends BaseViewModel {

    private AMapLocation aMapLocation;

    public MapSheetViewModel(@NonNull Application application) {
        super(application);
    }

    private LatLng defaultLocation = new LatLng(31.236255, 121.470231);
    public MutableLiveData<List<LocationInfo>> locationList = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(RespLocation resp) {
        List<LocationInfo> list = resp.getLocationInfoList();
        if (list != null) {
            LatLng mPoint = getMyLocation();
            LatLng latLng;//以我的位置为中心,计算距离
            for (int i = 0; i < list.size(); i++) {
                latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                list.get(i).setDistance(MathTool.div(AMapUtils.calculateLineDistance(latLng, mPoint) / 1000.0, 1, 2));
            }
        }
        locationList.setValue(list);
    }

    public void getLocation() {
        // TODO
//        BmobRequest.getInstance(getApplication()).getLocationInfo(RequestNo.LOCATION_INFO);
    }

    public void refreshMyLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    public LatLng getMyLocation() {
        LatLng mPoint;
        if (aMapLocation != null) {
            mPoint = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        } else {
            mPoint = defaultLocation;
        }
        return mPoint;
    }

    public LatLng getDefaultLocation() {
        return defaultLocation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public CharSequence getLocationAlertSpan() {
        String appName = getApplication().getString(R.string.app_name);
        String message = String.format(getApplication().getString(R.string.permission_request_LOCATION), appName);
        SpannableString span = new SpannableString(message);
        span.setSpan(new TextAppearanceSpan(getApplication(), R.style.text_color_2_15_style), 0, message.indexOf(appName), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int start = message.indexOf(appName) + appName.length();
        span.setSpan(new TextAppearanceSpan(getApplication(), R.style.text_color_black_style), message.indexOf(appName), start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new TextAppearanceSpan(getApplication(), R.style.text_color_2_15_style), start, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public void openGaoDeMap(View view, LatLng latLng, String describle) {
        try {
            if (!new File("/data/data/" + "com.autonavi.minimap").exists()) {
                SnackBarHelper.showSnackBar(view, "您还没有安装高德地图哟~", Snackbar.LENGTH_SHORT);
                return;
            }
            StringBuilder loc = new StringBuilder();
            loc.append("androidamap://viewMap?sourceApplication=XX");
            loc.append("&poiname=");
            loc.append(describle);
            loc.append("&lat=");
            loc.append(latLng.latitude);
            loc.append("&lon=");
            loc.append(latLng.longitude);
            loc.append("&dev=0");
            Intent intent = Intent.getIntent(loc.toString());
            getApplication().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}