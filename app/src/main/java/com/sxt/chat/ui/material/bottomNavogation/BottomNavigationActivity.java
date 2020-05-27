package com.sxt.chat.ui.material.bottomNavogation;

import android.app.Application;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomNavigationBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.chat.utils.Strings;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import android.view.View;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomNavigation
 */
public class BottomNavigationActivity extends BaseActivity<ActivityBottomNavigationBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_navigation;
    }

    @NotNull
    @Override
    public CardViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) App.getCtx());
        return createViewModel(this, factory, CardViewModel.class);
    }

    @Override
    public void initObserver() {
        super.initObserver();
        viewModel.cards.observe(this, this::bindCardMessage);
    }

    public void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setOnMenuItemClickListener(this::onMenuClick);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
        HideBottomViewOnScrollBehavior<View> behavior = new HideBottomViewOnScrollBehavior<>();
        params.setBehavior(behavior);
    }

    private boolean onMenuClick(MenuItem menuItem) {
        int labelVisibilityMode;
        switch (menuItem.getItemId()) {
            case R.id.labeled:
                labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED;
                break;
            case R.id.unlabeled:
                labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED;
                break;
            case R.id.selected:
                labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED;
                break;
            case R.id.auto:
            default:
                labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_AUTO;
                break;
        }
        binding.bottomNavigation.setLabelVisibilityMode(labelVisibilityMode);
        return false;
    }

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(this, messages));
    }
}