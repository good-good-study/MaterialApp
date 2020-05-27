package com.sxt.chat.ui.material.cards;

import android.app.Application;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.google.android.material.card.MaterialCardView;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.ActivityCardViewListDragBinding;
import com.sxt.chat.databinding.ActivityCardViewSelectableBinding;
import com.sxt.chat.ui.adapter.CardAdapter;
import com.sxt.chat.ui.adapter.CardListDragAdapter;
import com.sxt.chat.ui.adapter.CardSelectableAdapter;
import com.sxt.chat.view.draggable.ItemTouchHelperCallback;
import com.sxt.chat.view.draggable.RecyclerViewAccessibilityDelegateCompat;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseActivity;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * CardView -> List With Drag
 */
public class CardViewListDragActivity extends BaseActivity<ActivityCardViewListDragBinding, CardViewModel> {

    private CardListDragAdapter adapter;

    @Override
    public int getDisplayView() {
        return R.layout.activity_card_view_list_drag;
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
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.linearLayout:
                    notifyList(false);
                    break;
                case R.id.gridLayout:
                    notifyList(true);
                    break;
            }
            return false;
        });
    }

    private void notifyList(boolean isGrid) {
        RecyclerView.LayoutManager layoutManager;
        if (isGrid) {
            if (adapter.isGridLayout()) return;
            if (binding.recyclerView.getLayoutManager() instanceof GridLayoutManager) return;
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            if (!adapter.isGridLayout()) return;
            layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        }
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter.notifyLayoutManager(isGrid);
    }

    private void bindCardMessage(List<CardMessage> messages) {
        adapter = new CardListDragAdapter(this, messages);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setAccessibilityDelegateCompat(getDelegate(adapter));
        getItemTouchHelper(adapter).attachToRecyclerView(binding.recyclerView);
    }

    @NotNull
    private ItemTouchHelper getItemTouchHelper(CardListDragAdapter adapter) {
        return new ItemTouchHelper(new ItemTouchHelperCallback<>(adapter));
    }

    @NotNull
    private RecyclerViewAccessibilityDelegateCompat<CardMessage> getDelegate(CardListDragAdapter adapter) {
        return new RecyclerViewAccessibilityDelegateCompat<>(binding.recyclerView, adapter);
    }
}