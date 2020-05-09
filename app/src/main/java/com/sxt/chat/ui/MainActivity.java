package com.sxt.chat.ui;

import com.sxt.chat.R;
import com.sxt.chat.base.BaseActivity;
import com.sxt.chat.databinding.ActivityMainBinding;

/**
 * 主页
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int getDisplayView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initObserver() {

    }
}
