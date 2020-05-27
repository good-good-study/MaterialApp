package com.sxt.chat.ui;

import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityMainBinding;
import com.sxt.chat.ui.material.MaterialFragment;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * 主页
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int getDisplayView() {
        return R.layout.activity_main;
    }

    @NotNull
    @Override
    public MainViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, MainViewModel.class);
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
