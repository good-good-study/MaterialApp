package com.sxt.chat.base.viewmodel;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public interface IBase {
    //layout
    int getDisplayView();

    int getVariableId();

    //初始化view
    void initView();

    //初始化观察者
    void initObserver();
}