package com.sxt.chat.ui.material.tablayout;

import android.graphics.Color;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCollapsingTabBinding;
import com.sxt.chat.databinding.ItemPagerBinding;
import com.sxt.chat.utils.Strings;
import com.sxt.chat.utils.SystemUiStyle;
import com.sxt.chat.utils.html.Html;
import com.sxt.chat.utils.html.HtmlParser;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.view.BasePagerAdapter;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Created by xt.sun
 * 2020/5/10
 * CollapsingTab
 */
public class CollapsingTabActivity extends BaseActivity<ActivityCollapsingTabBinding, BaseViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_collapsing_tab;
    }

    @NotNull
    @Override
    public BaseViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, BaseViewModel.class);
    }

    public void initView() {
        setToolbar();
        setTabs();
        setViewPager();
    }

    private void setToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.fixed_width:
                    setTabFullWidth(false);
                    break;
                case R.id.full_width:
                    setTabFullWidth(true);
                    break;
                case R.id.badge_enable:
                    setBadgeEnable(binding.tabLayout);
                    break;
                case R.id.badge_disable:
                    setBadgeDisable(binding.tabLayout);
                    break;
            }
            return false;
        });
        binding.appBarLayout.addOnOffsetChangedListener((appBarLayout, offset) -> {
            if (appBarLayout.getTotalScrollRange() == Math.abs(offset)) {//完全折叠
                setLightStatusBar(true);
            } else if (offset == 0) {//完全展开
                setLightStatusBar(false);
            } else {//中间状态
                setLightStatusBar(false);
            }
        });
    }

    /**
     * set window statusBar style
     */
    private void setLightStatusBar(boolean isLight) {
        if (isLight) {
            SystemUiStyle.setLightStatusBar(this);
            SystemUiStyle.setStatusBarColor(this, android.R.color.white);
        } else {
            SystemUiStyle.setDarkStatusBar(this);
            SystemUiStyle.setStatusBarColor(this, android.R.color.transparent);
        }
    }

    private void setTabs() {
        TabLayout tabLayout = binding.tabLayout;
        List<String> list = Strings.generatedTabs();
        for (String text : list) {
            TabLayout.Tab tab = tabLayout.newTab()
                    .setText(text);
            tabLayout.addTab(tab);
            tab
                    .setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED)
                    .setIcon(R.drawable.ic_point_orange);
        }
        tabLayout.setTabIconTintResource(R.color.text_color_1);
        setBadgeEnable(tabLayout);
    }

    /**
     * set indicator full width
     */
    private void setTabFullWidth(boolean isFullWidth) {
        binding.tabLayout.setTabIndicatorFullWidth(isFullWidth);
        binding.tabLayout.requestLayout();
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
            badge.setVisible(false);
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
        List<String> data = Strings.generatedTabs();
        String[] titles = data.toArray(new String[]{});
        binding.viewPager.setAdapter(new BasePagerAdapter<String>(this, data, titles) {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ItemPagerBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_pager, container, true);
                Spanned spanned = HtmlParser.parseHtml(context, Html.TAB_LAYOUT);
                binding.text.setText(spanned, TextView.BufferType.SPANNABLE);
                return binding.getRoot();
            }
        });
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}