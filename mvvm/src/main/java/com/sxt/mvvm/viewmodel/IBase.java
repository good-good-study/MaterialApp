package com.sxt.mvvm.viewmodel;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public interface IBase<VM> {
    //layout
    @LayoutRes
    int getDisplayView();

    int getVariableId();

    //获取viewMode
    @NotNull
    VM initViewModel();

    //初始化view
    void initView();

    //初始化view
    void initView(@Nullable Bundle savedInstanceState);

    //初始化观察者
    void initObserver();
}