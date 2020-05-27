package com.sxt.chat.ui.material.toolbar;

import android.text.Spanned;
import android.widget.TextView;

import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityToolbarBinding;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.chat.utils.html.Html;
import com.sxt.chat.utils.html.HtmlParser;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xt.sun
 * 2020/5/10
 * MaterialToolbar
 */
public class ToolbarActivity extends BaseActivity<ActivityToolbarBinding, BaseViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_toolbar;
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
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.favorite:
                    SnackBarHelper.showSnackBarShort(binding.getRoot(), "favorite");
                    break;
                case R.id.search:
                    SnackBarHelper.showSnackBarShort(binding.getRoot(), "search");
                    break;
            }
            return false;
        });
    }

    private void loadHtml() {
        Spanned html = HtmlParser.parseHtml(this, Html.TOOLBAR);
        binding.text.setText(html, TextView.BufferType.SPANNABLE);
    }
}