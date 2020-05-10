package com.sxt.mvvm.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sxt.mvvm.base.viewmodel.BaseViewModel;
import com.sxt.mvvm.base.viewmodel.IBase;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kotlin.math.UMathKt;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends PermissionActivity implements IBase {
    public V binding;
    public VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        bindViewModel();
        initView();
        initObserver();
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
        Class<BaseViewModel> modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class<BaseViewModel>) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel.class;
        }
        viewModel = (VM) createViewModel(this, modelClass);
        //关联ViewModel
        binding.setVariable(getVariableId(), viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    @Override
    public int getVariableId() {
        return 0;
    }

    private ViewModel createViewModel(AppCompatActivity activity, Class<BaseViewModel> modelClass) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication()).create(modelClass);
    }
}