package com.sxt.chat.ui.material.bottomSheet;

import android.app.Application;
import android.graphics.Color;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomSheetBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomNavigation
 */
public class BottomSheetActivity extends BaseActivity<ActivityBottomSheetBinding, CardViewModel> {
    private BottomSheetBehavior<NavigationView> behavior;

    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_sheet;
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

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(this, messages));
    }

    public void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.fab.setColorFilter(Color.WHITE);
        initListener();
    }

    private void initListener() {
        binding.bottomAppbar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        behavior = BottomSheetBehavior.from(binding.navigationView);
        binding.bottomAppbar.setNavigationOnClickListener(v -> {
            if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            SnackBarHelper.showSnackBarAnchorShort(binding.fab, binding.fab, item.getTitle());
            return false;
        });
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (behavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }
    }
}