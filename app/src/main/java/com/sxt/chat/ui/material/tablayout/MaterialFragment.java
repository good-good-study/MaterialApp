package com.sxt.chat.ui.material.tablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.sxt.chat.R;
import com.sxt.chat.databinding.FragmentTablayoutBinding;
import com.sxt.chat.ui.material.chips.ChipsActivity;
import com.sxt.chat.utils.Strings;
import com.sxt.mvvm.base.ActivityCollector;

import java.util.List;
import java.util.Random;

/**
 * Created by xt.sun
 * 2020/5/10
 * Material组件
 */
public class MaterialFragment extends Fragment {
    private FragmentTablayoutBinding binding;
    private TabLayout auto;
    private TabLayout fixed;
    private TabLayout scrollable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tablayout, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.item_menu_tablayout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fixed_width:
                setTabFullWidth(false);
                break;
            case R.id.full_width:
                setTabFullWidth(true);
                break;
            case R.id.badge_enable:
                setBadgeEnable(auto);
                setBadgeEnable(fixed);
                setBadgeEnable(scrollable);
                break;
            case R.id.badge_disable:
                setBadgeDisable(auto);
                setBadgeDisable(fixed);
                setBadgeDisable(scrollable);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setToolbar();
        setTabs();
    }

    private void initView() {
        auto = binding.tabLayoutAuto;
        fixed = binding.tabLayoutFixed;
        scrollable = binding.tabLayoutScrollable;
    }

    private void setToolbar() {
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity == null) return;
        activity.setSupportActionBar(binding.toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) return;
        binding.toolbar.setElevation(8);
        binding.toolbar.setNavigationIcon(R.drawable.ic_navi_back);
        binding.toolbar.setNavigationOnClickListener(v -> {
            ActivityCollector.startActivity(getContext(), ChipsActivity.class);
        });
    }

    private void setTabs() {
        setTab(auto, Strings.generatedWords());
        setTab(fixed, Strings.generatedTabs());
        setTab(scrollable, Strings.generatedChips());
    }

    private void setTab(TabLayout layout, List<String> list) {
        for (String text : list) {
            layout.addTab(layout.newTab().setText(text));
        }
    }

    /**
     * set indicator full width
     */
    private void setTabFullWidth(boolean isFullWidth) {
        auto.setTabIndicatorFullWidth(isFullWidth);
        auto.requestLayout();
        fixed.setTabIndicatorFullWidth(isFullWidth);
        fixed.requestLayout();
        scrollable.setTabIndicatorFullWidth(isFullWidth);
        scrollable.requestLayout();
    }

    /**
     * hide Badge
     */
    private void setBadgeDisable(TabLayout layout) {
        for (int i = 0; i < layout.getTabCount(); i++) {
            TabLayout.Tab tab = layout.getTabAt(i);
            if (tab == null) continue;
            BadgeDrawable badge = tab.getOrCreateBadge();
            badge.clearNumber();
            badge.setVisible(false, true);
        }
    }

    /**
     * show Badge
     */
    private void setBadgeEnable(TabLayout layout) {
        for (int i = 0; i < layout.getTabCount(); i++) {
            TabLayout.Tab tab = layout.getTabAt(i);
            if (tab == null) continue;
            BadgeDrawable badge = tab.getOrCreateBadge();
            badge.setBackgroundColor(Color.RED);
            badge.setBadgeTextColor(Color.WHITE);
            badge.setMaxCharacterCount(3);
            badge.setNumber(i * (new Random().nextInt(100)) + 1);
            badge.setVisible(true, true);
        }
    }
}