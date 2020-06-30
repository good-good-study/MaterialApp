package com.sxt.chat.ui.material.bottomAppbar;

import android.app.Application;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomAppbarPositioningBinding;
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
 * BottomAppbar -> Positioning
 */
public class BottomAppbarPositioningActivity extends BaseActivity<ActivityBottomAppbarPositioningBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_appbar_positioning;
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
        binding.toolbar.setOnMenuItemClickListener(this::onMenuItemClick);
        binding.fab.setColorFilter(Color.WHITE);
        binding.fab.setOnClickListener(v -> showSnackBar("FAB"));
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.hide_fab:
                boolean willBeHidden = binding.fab.isOrWillBeHidden();
                if (willBeHidden) {
                    menuItem.setTitle("Hide  FAB");
                    binding.fab.show();
                } else {
                    binding.fab.hide();
                    menuItem.setTitle("SHOW  FAB");
                }
                break;
            case R.id.center:
                binding.bottomAppbar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                break;
            case R.id.end:
                binding.bottomAppbar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
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