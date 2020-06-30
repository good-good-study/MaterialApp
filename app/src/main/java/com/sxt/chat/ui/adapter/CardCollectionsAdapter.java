package com.sxt.chat.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.ViewUtils;
import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemListCardCollectionsBinding;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class CardCollectionsAdapter extends BaseRecyclerAdapter<CardMessage> {

    public CardCollectionsAdapter(Context context, List<CardMessage> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_list_card_collections, parent, false));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int marginMax = (int) ViewUtils.dpToPx(context, 16);
        int marginMin = (int) ViewUtils.dpToPx(context, 2);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        params.leftMargin = position == 0 ? marginMax : marginMin;
        params.rightMargin = position == getItemCount() - 1 ? marginMax : marginMin;
        holder.itemView.setLayoutParams(params);

        holder.binding.setCard(getItem(position));
        holder.binding.executePendingBindings();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListCardCollectionsBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}