package com.sxt.chat.ui.material.cards;

import android.app.Application;

import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewSelectableBinding;
import com.sxt.chat.ui.adapter.CardSelectableAdapter;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView -> Selectable
 */
public class CardViewSelectableActivity extends BaseActivity<ActivityCardViewSelectableBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_selectable;
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
        binding.recyclerView.setAdapter(new CardSelectableAdapter(this, messages));
    }
}