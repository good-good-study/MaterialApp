package com.sxt.mvvm.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.sxt.mvvm.viewmodel.BaseViewModel;
import com.sxt.mvvm.viewmodel.IBase;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends PermissionActivity implements IBase<VM> {
    public V binding;
    public VM viewModel;
    protected String TAG = this.getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        bindViewModel();
        initObserver();
        initView(savedInstanceState);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 关联DataBinding与ViewModel
     */
    private void bindViewModel() {
        binding = DataBindingUtil.setContentView(this, getDisplayView());
        //关联ViewModel
        viewModel = initViewModel();
        binding.setVariable(getVariableId(), viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initObserver() {

    }

    @Override
    public int getVariableId() {
        return 0;
    }

    public VM createViewModel(AppCompatActivity activity, ViewModelProvider.Factory factory, Class<VM> modelClass) {
        return new ViewModelProvider(activity, factory).get(modelClass);
    }
}