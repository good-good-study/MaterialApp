package com.sxt.chat.ui;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ActivityMainBinding;
import com.sxt.chat.ui.material.tablayout.MaterialFragment;
import com.sxt.mvvm.base.BaseActivity;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MaterialFragment())
                .commit();
    }

    @Override
    public void initObserver() {

    }
}
