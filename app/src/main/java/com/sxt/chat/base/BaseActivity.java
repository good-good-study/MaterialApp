package com.sxt.chat.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sxt.chat.base.viewmodel.BaseViewModel;
import com.sxt.chat.base.viewmodel.IBase;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends PermissionActivity implements IBase {
    V binding;
    VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
        initView();
        initObserver();
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