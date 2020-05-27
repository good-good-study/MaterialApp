package com.sxt.chat.ui.material.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.FragmentTablayoutBinding;
import com.sxt.chat.databinding.ItemPagerBinding;
import com.sxt.chat.ui.material.collapsing.CollapsingToolbarActivity;
import com.sxt.chat.utils.Strings;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.view.BasePagerAdapter;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Created by xt.sun
 * 2020/5/10
 * TabLayout
 */
public class TabLayoutActivity extends BaseActivity<FragmentTablayoutBinding, BaseViewModel> {
    private TabLayout auto;
    private TabLayout fixed;
    private TabLayout scrollable;

    @Override
    public int getDisplayView() {
        return R.layout.fragment_tablayout;
    }

    @NotNull
    @Override
    public BaseViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, BaseViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tablayout, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void initView() {
        auto = binding.tabLayoutAuto;
        fixed = binding.tabLayoutFixed;
        scrollable = binding.tabLayoutScrollable;
        setToolbar();
        setTabs();
        setViewPager();
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setElevation(8);
        binding.toolbar.setTitle("TabLayout");
        binding.toolbar.setNavigationIcon(R.drawable.ic_navi_back);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
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
            if (i == 0) {//显示自定义的文本
                badge.setContentDescriptionNumberless("·");
            } else {
                //显示数字气泡
                badge.setNumber(i * (new Random().nextInt(100)) + 1);
            }
            badge.setVisible(true);
        }
    }

    private void setViewPager() {
        List<String> data = Strings.generatedChips();
        String[] titles = data.toArray(new String[]{});
        binding.viewPager.setAdapter(new BasePagerAdapter<String>(this, data, titles) {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ItemPagerBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_pager, container, true);
                binding.text.setText(data.get(position));
                return binding.getRoot();
            }
        });
        scrollable.setupWithViewPager(binding.viewPager);
    }
}