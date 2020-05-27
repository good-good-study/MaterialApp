package com.sxt.chat.view.draggable;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.sxt.chat.R;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by google-material design
 */
public class RecyclerViewAccessibilityDelegateCompat<T> extends RecyclerViewAccessibilityDelegate {
    private RecyclerViewAccessibilityDelegateCompat(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
        this.recyclerView = recyclerView;
    }

    public RecyclerViewAccessibilityDelegateCompat(@NonNull RecyclerView recyclerView, BaseRecyclerAdapter<T> adapter) {
        super(recyclerView);
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<T> adapter;

    @NonNull
    @Override
    public AccessibilityDelegateCompat getItemDelegate() {
        return new ItemDelegate(this) {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                int position = recyclerView.getChildLayoutPosition(host);
                if (position != 0) {
                    info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.move_card_up_action,
                            "action_move_up"));
                }
                if (position != (recyclerView.getChildCount() - 1)) {
                    info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            R.id.move_card_down_action, "action_move_down"));
                }
            }

            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                int fromPosition = recyclerView.getChildLayoutPosition(host);
                if (action == R.id.move_card_down_action) {
                    swapCards(fromPosition, fromPosition + 1, adapter);
                    return true;
                } else if (action == R.id.move_card_up_action) {
                    swapCards(fromPosition, fromPosition - 1, adapter);
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        };
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