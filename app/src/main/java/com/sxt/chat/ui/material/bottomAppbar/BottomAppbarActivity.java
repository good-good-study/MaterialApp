package com.sxt.chat.ui.material.bottomAppbar;

import android.app.Application;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomAppbarBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.chat.utils.Strings;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomAppbar scrolling
 */
public class BottomAppbarActivity extends BaseActivity<ActivityBottomAppbarBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_appbar;
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
        binding.bottomAppbar.setNavigationOnClickListener(v -> showSnackBar("menu"));
        binding.fab.setColorFilter(Color.WHITE);
        binding.fab.setOnClickListener(v -> showSnackBar("FAB"));
        binding.bottomAppbar.setOnMenuItemClickListener(this::onMenuItemClick);
        binding.toolbar.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.hide_on_scroll:
                boolean hideOnScroll = binding.bottomAppbar.getHideOnScroll();
                menuItem.setTitle(hideOnScroll ? "HIDE_ON_SCROLL" : "SHOW_ON_SCROLL");
                binding.bottomAppbar.setHideOnScroll(!hideOnScroll);
                break;
            case R.id.center:
                binding.bottomAppbar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                break;
            case R.id.end:
                binding.bottomAppbar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                break;
            case R.id.hide_fab:
                if (binding.fab.isOrWillBeHidden()) {
                    binding.fab.show();
                    menuItem.setTitle("Hide FAB");
                } else {
                    binding.fab.hide();
                    menuItem.setTitle("Show FAB");
                }
                break;
        }
        return false;
    }

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(this, messages));
    }

    private void showSnackBar(String message) {
        View view = binding.fab.getVisibility() == View.VISIBLE ? binding.fab : binding.bottomAppbar;
        SnackBarHelper.showSnackBarAnchorShort(view, view, message);
    }
}