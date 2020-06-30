package com.sxt.chat.ui.material.bottomNavogation;

import android.app.Application;
import android.transition.ChangeTransform;
import android.transition.TransitionManager;

import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.FragmentTransitionBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.material.cards.CardViewModel;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/23
 */
public class TransitionFragment extends BaseFragment<FragmentTransitionBinding, CardViewModel> {

    @Override
    public int getDisplayView() {
        return R.layout.fragment_transition;
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

    private void bindCardMessage(List<CardMessage> messages) {
        binding.recyclerView.setAdapter(new CardAdapter(getContext(), messages));
    }

    @Override
    public void initView() {
        binding.coordinatorLayout.setAlpha(1.0f);
        TransitionManager.beginDelayedTransition(binding.coordinatorLayout, new ChangeTransform());
    }
}
