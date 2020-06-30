package com.sxt.chat.ui.material.cards;

import android.app.Application;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.card.MaterialCardView;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewSwipeBinding;
import com.sxt.chat.databinding.ItemCardSwipeBinding;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import android.transition.TransitionManager;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView -> Swipe Dismiss
 */
public class CardViewSwipeActivity extends BaseActivity<ActivityCardViewSwipeBinding, CardViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_swipe;
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
        insertViews();
    }

    private void bindCardSwipe(MaterialCardView cardView) {
        SwipeDismissBehavior<View> behavior = new SwipeDismissBehavior<>();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) cardView.getLayoutParams();
        params.setBehavior(behavior);

        behavior.setDragDismissDistance(0.8f);
        behavior.setStartAlphaSwipeDistance(1.0f);
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                View parent = (View) view.getParent();
                int index = binding.swipeContainer.indexOfChild(parent);
                binding.swipeContainer.removeView(parent);
                if (binding.swipeContainer.getChildCount() == 0) {
                    insertViews();
                }
                SnackBarHelper.showSnackBarAnchorShort(binding.nestedScrollview, null, "view has deleted", "Undo", v -> {
                    //恢复已删除的view
                    binding.swipeContainer.addView(parent, index);
                    view.setAlpha(1.0f);
                });
            }

            @Override
            public void onDragStateChanged(int state) {
                switch (state) {
                    case SwipeDismissBehavior.STATE_DRAGGING:
                    case SwipeDismissBehavior.STATE_SETTLING:
                        cardView.setDragged(true);
                        break;
                    case SwipeDismissBehavior.STATE_IDLE:
                        cardView.setDragged(false);
                        break;
                    default: // fall out
                }
            }
        });
    }

    private void insertViews() {
        for (int i = 0; i < 10; i++) {
            TransitionManager.beginDelayedTransition(binding.swipeContainer);
            ItemCardSwipeBinding cardSwipeBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_card_swipe, binding.swipeContainer, true);
            bindCardSwipe(cardSwipeBinding.cardView);
        }
    }
}