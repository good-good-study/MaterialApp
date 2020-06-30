package com.sxt.chat.ui.material.cards;

import android.animation.LayoutTransition;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewDraggableBinding;
import com.sxt.chat.view.draggable.DraggableCoordinatorLayout;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView -> Draggable
 */
public class CardViewDraggableActivity extends BaseActivity<ActivityCardViewDraggableBinding, CardViewModel> {
    private MaterialCardView cardView;
    private DraggableCoordinatorLayout coordinatorLayout;

    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_draggable;
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
        coordinatorLayout = binding.coordinatorLayout;
        cardView = binding.cardDraggable.cardView;
    }

    private void bindCardMessage(List<CardMessage> messages) {
        coordinatorLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        coordinatorLayout.addDraggableChild(cardView);
        coordinatorLayout.setViewDragListener(
                new DraggableCoordinatorLayout.ViewDragListener() {
                    @Override
                    public void onViewCaptured(@NonNull View view, int i) {
                        cardView.setDragged(true);
                    }

                    @Override
                    public void onViewReleased(@NonNull View view, float v, float v1) {
                        cardView.setDragged(false);
                    }
                });
    }
}