package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemListCardDragBinding;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

import cn.bmob.v3.util.V;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class CardListDragAdapter extends BaseRecyclerAdapter<CardMessage> {
    public CardListDragAdapter(Context context, List<CardMessage> data) {
        super(context, data);
    }

    private boolean isGridLayout;

    public boolean isGridLayout() {
        return isGridLayout;
    }

    public void notifyLayoutManager(boolean isGridLayout) {
        this.isGridLayout = isGridLayout;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView recyclerView = (RecyclerView) parent;
        isGridLayout = recyclerView.getLayoutManager() instanceof GridLayoutManager;
        return new ViewHolder(getInflater().inflate(R.layout.item_list_card_drag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        CardMessage card = getItem(position);
        holder.binding.setTitle(card.getTitle());
        holder.binding.setMessage(card.getMessage());
        holder.binding.dragHandle.setVisibility(isGridLayout ? View.GONE : View.VISIBLE);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListCardDragBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}