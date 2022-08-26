package com.sxt.chat.ui.material.bottomSheet;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.snackbar.Snackbar;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityMapBottomSheetBinding;
import com.sxt.chat.ui.adapter.LocationAdapter;
import com.sxt.chat.utils.AnimationUtil;
import com.sxt.chat.utils.LocationManager;
import com.sxt.chat.utils.LogUtil;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.chat.utils.SystemUiStyle;
import com.sxt.chat.view.dectoration.DividerItemDecoration;
import com.sxt.chat.view.listener.SimpleTextWatcher;
import com.sxt.mvvm.model.location.LocationInfo;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxt on 2018/10/26.
 * BottomSheet Map
 */
public class MapSheetActivity extends BaseActivity<ActivityMapBottomSheetBinding, MapSheetViewModel> implements LocationSource {
    private AMap aMap;
    private LocationAdapter adapter;
    private BottomSheetBehavior<View> behavior;

    private ValueAnimator valueAnimator;
    private float mAnimatorValue;
    private final int maxRadius = 100;
    private final int ZOOM = 17;
    private int peekHeight;
    private final List<Circle> listCircle = new ArrayList<>();
    private final int REQUEST_CODE_LOCATION = 500;
    private int screenHeight;

    @Override
    public int getDisplayView() {
        return R.layout.activity_map_bottom_sheet;
    }

    @NotNull
    @Override
    public MapSheetViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) App.getCtx());
        return createViewModel(this, factory, MapSheetViewModel.class);
    }

    @Override
    public void initObserver() {
        super.initObserver();
        viewModel.locationList.observe(this, this::refreshLocation);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        binding.mapView.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        SystemUiStyle.fitSystemWindow(this);
        SystemUiStyle.setStatusBarColor(this, R.color.alpha_shader);
        SystemUiStyle.setLightStatusBar(this);
        //地图的夜间模式跟随app当前的模式
//        boolean isNightMode = Prefs.getInstance(this).isNightMode();
        aMap = binding.mapView.getMap();
//        aMap.setMapType(isNightMode ? AMap.MAP_TYPE_NIGHT : AMap.MAP_TYPE_NORMAL);
        initListener();
        initBehavior();
        initMap();
    }

    private void initListener() {
        binding.close.setOnClickListener(v -> {
            binding.searchView.setText("");
            binding.searchView.clearFocus();
        });
        binding.searchView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                boolean isEmpty = TextUtils.isEmpty(s.toString().trim());
                float startAngle = binding.close.getRotation();
                float endAngle = isEmpty ? 0 : 90;
                AnimationUtil.rotationInput(binding.close, startAngle, endAngle, isEmpty);
            }
        });
        binding.itemChips.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip == null) return;
            if (TextUtils.isEmpty(chip.getText())) return;
            binding.searchView.setText(chip.getText());
            binding.searchView.clearFocus();
        });

        int color = ContextCompat.getColor(this, R.color.white);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        binding.fabScrolling.setBackgroundTintList(colorStateList);
        binding.fabMyLocation.setBackgroundTintList(colorStateList);

        binding.fabMyLocation.setOnClickListener(view -> requestPermission());
        binding.fabScrolling.setOnClickListener(v -> {
            LatLng latLng = viewModel.getMyLocation();
            viewModel.openGaoDeMap(binding.coordinator, latLng, getString(R.string.app_name));
        });
        LocationManager.getInstance(this).setOnLocationListener(this::onLocationResult);
    }

    /**
     * 初始化BottomSheet
     */
    @SuppressLint("RestrictedApi")
    private void initBehavior() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        peekHeight = (int) ViewUtils.dpToPx(this, 100);

        behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.addBottomSheetCallback(bottomSheetCallback());
        binding.bottomSheet.post(() -> {
            //设置bottomSheet的总高度
            ViewGroup.LayoutParams params = binding.bottomSheet.getLayoutParams();
            params.height = screenHeight;
            binding.bottomSheet.setLayoutParams(params);

            behavior.setFitToContents(false);//展开后开度填充Parent的高度
            //setFitToContents 为false时，展开后距离顶部的位置（Parent会以PaddingTop填充）
            behavior.setExpandedOffset(64);
            behavior.setHalfExpandedRatio(0.45f);
            behavior.setHideable(true);
            behavior.setPeekHeight(peekHeight, true);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }

    private void initMap() {
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setScaleControlsEnabled(true);// 标尺开关
        uiSettings.setZoomControlsEnabled(false);//缩放按钮
        aMap.setLocationSource(this);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM));
    }

    @NotNull
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback() {
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if (binding.bottomSheet.getScrollY() != 0) {
                            binding.bottomSheet.setScrollY(0);//列表滑动到顶端
                        }
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        if (binding.cardView.getTranslationY() != 0) {
                            binding.cardView.setTranslationY(0);
                        }
                        break;
                }
                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    if (binding.fabContainer.getVisibility() != View.INVISIBLE) {
                        binding.fabContainer.setVisibility(View.INVISIBLE);
                    }
                } else if (binding.fabContainer.getVisibility() != View.VISIBLE) {
                    binding.fabContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float distance;
                if (slideOffset > 0) {//在peekHeight位置以上 滑动(向上、向下) slideOffset bottomSheet.getHeight() 是展开后的高度的比例
                    int height = bottomSheet.getHeight();
                    distance = height * slideOffset;
                    //地图跟随滑动，将我的位置移动到中心
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM));
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(viewModel.getMyLocation()));
                    binding.mapView.scrollTo(0, -(int) (distance / 2f));
                    binding.mapView.setTranslationY(-distance);
                    //搜索栏反向位移
                    float halfExpandedRatio = behavior.getHalfExpandedRatio();
                    float halfHeight = halfExpandedRatio * height;//中间状态的高度
                    if (distance > halfHeight) {
                        binding.cardView.setTranslationY(-(distance - halfHeight));
                        binding.cardView.setAlpha(1 - slideOffset + halfExpandedRatio);
                        binding.itemChips.getRoot().setAlpha(1 - slideOffset);
                    } else {
                        binding.cardView.setTranslationY(0);
                        binding.cardView.setAlpha(1.0f);
                        binding.itemChips.getRoot().setAlpha(1.0f);
                    }
                } else {//在peekHeight位置以下 滑动(向上、向下)  slideOffset 是PeekHeight的高度的比例
                    distance = behavior.getPeekHeight() * slideOffset;
                }
                if (distance < 0) {
                    binding.fabContainer.setTranslationY(-distance);
                } else if (distance <= peekHeight) {
                    //右侧按钮最大可位移高度为peekHeight
                    binding.fabContainer.setTranslationY(-distance);
                }
            }
        };
    }

    private void refreshLocation(List<LocationInfo> list) {
        refreshMyLocation();
        if (adapter == null) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
            DividerItemDecoration decoration = new DividerItemDecoration(this, drawable);
            binding.recyclerView.addItemDecoration(decoration);
            adapter = new LocationAdapter(this, list);
            adapter.setContentObserver(this::refreshState);
            adapter.setOnItemClickListener((position, info) -> {
                LatLng latLng = new LatLng(info.getLatitude(), info.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM));
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            });
            binding.recyclerView.setAdapter(adapter);
        } else {
            adapter.resetIndex();
            adapter.notifyDataSetChanged(list);
        }
    }

    private void refreshState(int count) {
        if (count == 0) {
            binding.viewSwitcher.setDisplayedChild(1);
            behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        } else {
            binding.viewSwitcher.setDisplayedChild(0);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    /**
     * 绘制我的位置
     */
    private void refreshMyLocation() {
        LatLng latLng = viewModel.getMyLocation();
        aMap.clear();
        clearCircle();
        // 绘制不同半径的圆，添加到地图上
        final Circle circleOut = aMap.addCircle(new CircleOptions().center(latLng)
                .radius(maxRadius)
                .strokeColor(ContextCompat.getColor(this, android.R.color.transparent))
                .fillColor(ContextCompat.getColor(this, R.color.blue_shader))
                .strokeWidth(0));

        final Circle circleIn = aMap.addCircle(new CircleOptions().center(latLng)
                .radius(10)
                .strokeColor(ContextCompat.getColor(this, android.R.color.transparent))
                .fillColor(ContextCompat.getColor(this, R.color.green_shader))
                .strokeWidth(0));

        aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .setFlat(true)//平贴地面
                .icon(BitmapDescriptorFactory.fromView(View.inflate(this, R.layout.item_location_point, null)))
                .position(latLng));

        listCircle.add(circleOut);
        listCircle.add(circleIn);
        startAnimator(circleIn);
    }

    private void clearCircle() {
        if (valueAnimator != null && valueAnimator.isRunning()) valueAnimator.cancel();
        for (int i = 0; i < listCircle.size(); i++) listCircle.get(i).remove();
        listCircle.clear();
    }

    private void startAnimator(final Circle circle) {
        valueAnimator = ValueAnimator.ofFloat(10, maxRadius).setDuration(3000);
        valueAnimator.addUpdateListener(animation -> {
            mAnimatorValue = (float) animation.getAnimatedValue();
            circle.setRadius(mAnimatorValue);
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);//反向重复执行,可以避免抖动
        valueAnimator.start();
    }

    /**
     * 发起定位
     */
    private void startLocation() {
        LocationManager.getInstance(this).startLocation();
    }

    /**
     * 解析定位数据
     */
    private void onLocationResult(AMapLocation aMapLocation) {
        if (aMapLocation == null) return;
        LatLng latLng;
        viewModel.refreshMyLocation(aMapLocation);
        if (aMapLocation.getErrorCode() == 0) {
            latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        } else {
            latLng = viewModel.getDefaultLocation();
            SnackBarHelper.showSnackBar(binding.coordinator, aMapLocation.getErrorInfo(), Snackbar.LENGTH_SHORT);
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            LogUtil.e(TAG, "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

        List<LocationInfo> list = new ArrayList<>();
        LocationInfo info = new LocationInfo();
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        refreshLocation(list);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        LocationManager.getInstance(this).stopLocation();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        requestPermission();
    }

    @Override
    public void deactivate() {
        LocationManager.getInstance(this).releaseClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
        stopLocation();
    }

    @Override
    public void onBackPressed() {
        switch (behavior.getState()) {
            case BottomSheetBehavior.STATE_EXPANDED:
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                break;
            case BottomSheetBehavior.STATE_HALF_EXPANDED:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        LocationManager.getInstance(this).releaseClient();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    @Override
    public void onPermissionsAllowed(int requestCode, String[] permissions, int[] grantResults) {
        super.onPermissionsAllowed(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            startLocation();
        }
    }

    @Override
    public void onPermissionsRefusedNever(int requestCode, String[] permissions, int[] grantResults) {
        super.onPermissionsRefusedNever(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            showPermissionRefusedNeverDialog(viewModel.getLocationAlertSpan());
        }
    }

    /**
     * 请求位置权限
     */
    private void requestPermission() {
        boolean hasPermission = checkPermissions(REQUEST_CODE_LOCATION, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        });
        if (hasPermission) startLocation();
    }

    /**
     * 权限被彻底禁止后 , 弹框提醒用户去开启
     */
    private void showPermissionRefusedNeverDialog(CharSequence message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("温馨提示")
                .setMessage(message)
                .setPositiveButton("去设置", (dialog, which) -> {
                    dialog.dismiss();
                    goToAppSettingsPage();
                }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .show();
    }
}