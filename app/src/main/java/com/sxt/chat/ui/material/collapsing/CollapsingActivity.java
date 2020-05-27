package com.sxt.chat.ui.material.collapsing;

import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCollapsingBinding;
import com.sxt.chat.utils.SystemUiStyle;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xt.sun
 * 2020/5/10
 * Collapsing
 */
public class CollapsingActivity extends BaseActivity<ActivityCollapsingBinding, BaseViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_collapsing;
    }

    @NotNull
    @Override
    public BaseViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, BaseViewModel.class);
    }

    public void initView() {
        setToolbar();
    }

    private void setToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.appBarLayout.addOnOffsetChangedListener((appBarLayout, offset) -> {
            if (appBarLayout.getTotalScrollRange() == Math.abs(offset)) {//完全折叠
                setLightStatusBar(true);
            } else if (offset == 0) {//完全展开
                setLightStatusBar(false);
            } else {//中间状态
                setLightStatusBar(false);
            }
        });
    }

    /**
     * set window statusBar style
     */
    private void setLightStatusBar(boolean isLight) {
        if (isLight) {
            SystemUiStyle.setLightStatusBar(this);
            SystemUiStyle.setStatusBarColor(this, android.R.color.white);
        } else {
            SystemUiStyle.setDarkStatusBar(this);
            SystemUiStyle.setStatusBarColor(this, android.R.color.transparent);
        }
    }
}