package com.sxt.mvvm.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by xt.sun
 * 2020/5/9
 * ViewModel关联view生命周期 基类
 */
public abstract class IBaseViewModel extends AndroidViewModel implements IBaseObserver {
    IBaseViewModel(@NonNull Application application) {
        super(application);
    }

    final String TAG = this.getClass().getName();

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }
}