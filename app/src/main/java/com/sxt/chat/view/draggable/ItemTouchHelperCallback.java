package com.sxt.chat.view.draggable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * created by google-material design
 * RecyclerView item拖拽排序回调
 */
public class ItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {
    private final BaseRecyclerAdapter<T> cardAdapter;
    private static final int SWIPE_FLAGS = 0;
    //item拖动时 位置变动方向
    private static final int DRAG_FLAGS = ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN;

    @Nullable
    private MaterialCardView dragCardView;

    public ItemTouchHelperCallback(BaseRecyclerAdapter<T> cardAdapter) {
        this.cardAdapter = cardAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(DRAG_FLAGS, SWIPE_FLAGS);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getBindingAdapterPosition();
        int toPosition = target.getBindingAdapterPosition();
        swapCards(fromPosition, toPosition, cardAdapter);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
            dragCardView = (MaterialCardView) viewHolder.itemView;
            dragCardView.setDragged(true);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && dragCardView != null) {
            dragCardView.setDragged(false);
            dragCardView = null;
        }
    }

    /**
     * 排序后，交换数据，刷新列表
     */
    private void swapCards(int fromPosition, int toPosition, BaseRecyclerAdapter<T> adapter) {
        List<T> data = adapter.getData();
        T fromData = data.get(fromPosition);
        T toData = data.get(toPosition);

        data.remove(fromPosition);
        data.add(fromPosition, toData);

        data.remove(toPosition);
        data.add(toPosition, fromData);

        adapter.notifyItemMoved(fromPosition, toPosition);
    }
}