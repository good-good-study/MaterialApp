package com.sxt.chat.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.sxt.chat.base.viewmodel.IBaseObserver;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public class BaseViewModel extends IBaseViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}