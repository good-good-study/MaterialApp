package com.sxt.chat.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sxt.chat.ui.MainViewModel;
import com.sxt.chat.ui.material.MaterialViewModel;
import com.sxt.chat.ui.material.bottomSheet.MapSheetViewModel;
import com.sxt.chat.ui.material.cards.CardCategoryViewModel;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.chat.ui.material.chips.ChipsViewModel;
import com.sxt.mvvm.viewmodel.BaseViewModel;

/**
 * Created by xt.sun
 * 2020/5/19
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private String repository;
    private Application application;
    private static ViewModelFactory INSTANCE;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application, "");
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application, String repository) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BaseViewModel.class)) {
            return (T) new BaseViewModel(application);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(application);
        } else if (modelClass.isAssignableFrom(MaterialViewModel.class)) {
            return (T) new MaterialViewModel(application);
        } else if (modelClass.isAssignableFrom(CardViewModel.class)) {
            return (T) new CardViewModel(application);
        } else if (modelClass.isAssignableFrom(CardCategoryViewModel.class)) {
            return (T) new CardCategoryViewModel(application);
        } else if (modelClass.isAssignableFrom(ChipsViewModel.class)) {
            return (T) new ChipsViewModel(application);
        } else if (modelClass.isAssignableFrom(MapSheetViewModel.class)) {
            return (T) new MapSheetViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class => " + modelClass);
    }
}
