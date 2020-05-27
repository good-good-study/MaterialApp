package com.sxt.chat.ui.material.collapsing;

import android.text.Spanned;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCollapsingToolbarBinding;
import com.sxt.chat.utils.html.Html;
import com.sxt.chat.utils.html.HtmlParser;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xt.sun
 * 2020/5/10
 * CollapsingToolbar
 */
public class CollapsingToolbarActivity extends BaseActivity<ActivityCollapsingToolbarBinding, BaseViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_collapsing_toolbar;
    }

    @NotNull
    @Override
    public BaseViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, BaseViewModel.class);
    }

    public void initView() {
        setToolbar();
        loadHtml();
    }

    private void setToolbar() {
        binding.toolbar.setElevation(0);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                //设置折叠模式
                case R.id.none:
                    setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF);
                    break;
                case R.id.pin:
                    setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
                    break;
                case R.id.parallax:
                    setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX);
                    break;
                //设置滑动行为
                case R.id.enterAlways:
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    break;
                case R.id.enterAlwaysCollapsed:
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                    break;
                case R.id.exitUntilCollapsed:
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    break;
                case R.id.snap://吸附效果（下拉回弹）
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    break;
                case R.id.snapExitUtilCollapsed:
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    break;
                case R.id.snapMargins:
                    setLayoutFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS);
                    break;
            }
            return false;
        });
    }

    /**
     * 设置滑动行为
     */
    private void setLayoutFlags(int scrollFlags) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.collapsing.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | scrollFlags);
        binding.collapsing.setLayoutParams(params);
    }

    /**
     * 设置折叠模式
     */
    private void setCollapseMode(int collapseMode) {
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        if (collapseMode == CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX) {
            params.setParallaxMultiplier(0.8f);
        }
        params.setCollapseMode(collapseMode);
        binding.toolbar.setLayoutParams(params);
    }

    private void loadHtml() {
        Spanned html = HtmlParser.parseHtml(this, Html.COLLAPSING);
        binding.text.setText(html, TextView.BufferType.SPANNABLE);
    }
}