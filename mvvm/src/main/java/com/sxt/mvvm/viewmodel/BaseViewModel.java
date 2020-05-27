package com.sxt.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public class BaseViewModel extends IBaseViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}