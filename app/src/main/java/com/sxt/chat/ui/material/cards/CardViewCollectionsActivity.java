package com.sxt.chat.ui.material.cards;

import android.app.Application;

import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewBinding;
import com.sxt.chat.databinding.ActivityCardViewCollectionsBinding;
import com.sxt.chat.databinding.ActivityCardViewCollectionsBindingImpl;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.adapter.CardCollectionsAdapter;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView Collections
 */
public class CardViewCollectionsActivity extends BaseActivity<ActivityCardViewCollectionsBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_collections;
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
        binding.recyclerView.setAdapter(new CardCollectionsAdapter(this, messages));
    }
}