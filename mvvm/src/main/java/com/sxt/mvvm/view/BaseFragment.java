package com.sxt.mvvm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sxt.mvvm.viewmodel.BaseViewModel;
import com.sxt.mvvm.viewmodel.IBase;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xt.sun
 * 2020/5/16
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBase<VM> {
    public V binding;
    public VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindViewModel(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObserver();
        initView();
    }

    /**
     * 关联DataBinding与ViewModel
     */
    private void bindViewModel(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, getDisplayView(), container, false);
        //关联ViewModel
        viewModel = initViewModel();
        binding.setVariable(getVariableId(), viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    @Override
    public int getVariableId() {
        return 0;
    }

    @Override
    public void initObserver() {

    }

    public VM createViewModel(BaseFragment fragment, ViewModelProvider.Factory factory, Class<VM> modelClass) {
        return new ViewModelProvider(fragment, factory).get(modelClass);
    }
}
