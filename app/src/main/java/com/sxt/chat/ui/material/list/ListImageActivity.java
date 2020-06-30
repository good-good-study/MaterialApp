package com.sxt.chat.ui.material.list;

import android.app.Application;

import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityListImageBinding;
import com.sxt.chat.ui.adapter.ImageListAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/6/01
 * RecyclerView image list
 */
public class ListImageActivity extends BaseActivity<ActivityListImageBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_list_image;
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
    }

    private void bindCardMessage(List<CardMessage> messages) {
        ArrayList<CardMessage> list = new ArrayList<>();
        list.addAll(messages);
        list.addAll(messages);
        list.addAll(messages);
        binding.recyclerView.setAdapter(new ImageListAdapter(this, list));
    }
}