package com.sxt.chat.ui.material.bottomNavogation;

import android.app.Application;
import android.transition.ChangeTransform;
import android.transition.TransitionManager;
import android.view.MenuItem;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityBottomNavigationBadgeBinding;
import com.sxt.chat.databinding.ActivityBottomNavigationTransitionBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.chat.utils.Strings;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.view.BaseFragment;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import android.view.View;

/**
 * Created by xt.sun
 * 2020/5/10
 * BottomNavigation -> Transitions
 */
public class BottomNavigationTransitionsActivity extends BaseActivity<ActivityBottomNavigationTransitionBinding, BaseViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_bottom_navigation_transition;
    }

    @NotNull
    private Map<Integer, BaseFragment> fragments = new HashMap<>();

    @NotNull
    @Override
    public BaseViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) App.getCtx());
        return createViewModel(this, factory, BaseViewModel.class);
    }

    public void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemClick);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
        HideBottomViewOnScrollBehavior<View> behavior = new HideBottomViewOnScrollBehavior<>();
        params.setBehavior(behavior);

        fragments.put(R.id.favorite, new TransitionFragment());
        fragments.put(R.id.music, new TransitionFragment());
        fragments.put(R.id.places, new TransitionFragment());
        fragments.put(R.id.news, new TransitionFragment());
        BaseFragment fragment = fragments.get(R.id.favorite);
        beginTransition(fragment);
    }

    private boolean onNavigationItemClick(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
        }
        BaseFragment fragment = fragments.get(item.getItemId());
        if (fragment != null) {
            beginTransition(fragment);
        }
        BadgeDrawable badge = binding.bottomNavigation.getBadge(item.getItemId());
        if (badge != null) {
            badge.setVisible(false);
            badge.clearNumber();
        }
        return false;
    }

    private void beginTransition(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}