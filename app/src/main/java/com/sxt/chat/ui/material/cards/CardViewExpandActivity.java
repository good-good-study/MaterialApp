package com.sxt.chat.ui.material.cards;

import android.app.Application;

import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewExpandBinding;
import com.sxt.chat.ui.adapter.CardExpandAdapter;
import com.sxt.chat.ui.adapter.listener.EventListener;
import com.sxt.chat.view.transition.FadeInTransition;
import com.sxt.chat.view.transition.FadeOutTransition;
import com.sxt.mvvm.model.card.CardComment;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView -> Expand
 */
public class CardViewExpandActivity extends BaseActivity<ActivityCardViewExpandBinding, CardCategoryViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_expand;
    }

    @NotNull
    @Override
    public CardCategoryViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) App.getCtx());
        return createViewModel(this, factory, CardCategoryViewModel.class);
    }

    @Override
    public void initObserver() {
        super.initObserver();
        viewModel.comments.observe(this, this::bindComments);
    }

    public void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void bindComments(List<CardComment> comments) {
        CardExpandAdapter adapter = new CardExpandAdapter(this, comments);
        binding.recyclerView.setAdapter(adapter);
        adapter.setListener(getEventListener());
    }

    @NotNull
    private EventListener getEventListener() {
        return new EventListener() {
            @Override
            public void onExpand() {
                TransitionManager.beginDelayedTransition(binding.coordinatorLayout, getExpandTransition());
            }

            @Override
            public void onCollapse() {
                TransitionManager.beginDelayedTransition(binding.coordinatorLayout, getCollapseTransition());
            }
        };
    }

    @NotNull
    private Transition getExpandTransition() {
        FadeInTransition transition = new FadeInTransition();
        transition.setDuration(300);
        return transition;
    }

    @NotNull
    private Transition getCollapseTransition() {
        FadeOutTransition transition = new FadeOutTransition();
        transition.setDuration(200);
        return transition;
    }
}