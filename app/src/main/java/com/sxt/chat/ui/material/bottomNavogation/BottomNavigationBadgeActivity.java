package com.sxt.chat.ui.material.bottomNavogation;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomNavigationBadgeBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomNavigation -> Badge
 */
public class BottomNavigationBadgeActivity extends BaseActivity<ActivityBottomNavigationBadgeBinding, CardViewModel> {
    private int badge_gravity = BadgeDrawable.TOP_END;

    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_navigation_badge;
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
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemClick);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
        HideBottomViewOnScrollBehavior<View> behavior = new HideBottomViewOnScrollBehavior<>();
        params.setBehavior(behavior);
        resetBadges();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.reset_badges:
                resetBadges();
                break;
            case R.id.top_end:
                badge_gravity = BadgeDrawable.TOP_END;
                resetBadges();
                break;
            case R.id.top_start:
                badge_gravity = BadgeDrawable.TOP_START;
                resetBadges();
                break;
            case R.id.bottom_end:
                badge_gravity = BadgeDrawable.BOTTOM_END;
                resetBadges();
                break;
            case R.id.bottom_start:
                badge_gravity = BadgeDrawable.BOTTOM_START;
                resetBadges();
                break;
        }
        return false;
    }

    private void resetBadges() {
        BadgeDrawable badgeFavorite = binding.bottomNavigation.getOrCreateBadge(R.id.favorite);
        badgeFavorite.setBadgeGravity(badge_gravity);
        badgeFavorite.setVisible(true);
        badgeFavorite.setContentDescriptionNumberless("·");

        BadgeDrawable badgeMusic = binding.bottomNavigation.getOrCreateBadge(R.id.music);
        badgeMusic.setBadgeGravity(badge_gravity);
        badgeMusic.setVisible(true);
        badgeMusic.setNumber(9);

        BadgeDrawable badgePlaces = binding.bottomNavigation.getOrCreateBadge(R.id.places);
        badgePlaces.setBadgeGravity(badge_gravity);
        badgePlaces.setVisible(true);
        badgePlaces.setNumber(999);

        BadgeDrawable badgeNews = binding.bottomNavigation.getOrCreateBadge(R.id.news);
        badgeNews.setBadgeGravity(badge_gravity);
        badgeNews.setVisible(true);
        badgeNews.setNumber(9999);
    }

    private boolean onNavigationItemClick(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
        }
        BadgeDrawable badge = binding.bottomNavigation.getBadge(item.getItemId());
        if (badge != null) {
            badge.setVisible(false);
            badge.clearNumber();
        }
        return false;
    }

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(this, messages));
    }
}