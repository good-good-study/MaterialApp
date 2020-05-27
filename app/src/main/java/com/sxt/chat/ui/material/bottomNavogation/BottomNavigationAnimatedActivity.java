package com.sxt.chat.ui.material.bottomNavogation;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomNavigationAnimatedBinding;
import com.sxt.chat.databinding.ActivityBottomNavigationBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomNavigation -> Animated
 */
public class BottomNavigationAnimatedActivity extends BaseActivity<ActivityBottomNavigationAnimatedBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_navigation_animated;
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
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
        HideBottomViewOnScrollBehavior<View> behavior = new HideBottomViewOnScrollBehavior<>();
        params.setBehavior(behavior);
    }

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(this, messages));
    }
}