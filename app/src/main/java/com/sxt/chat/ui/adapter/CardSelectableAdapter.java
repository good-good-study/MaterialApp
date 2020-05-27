package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemListCardBinding;
import com.sxt.chat.databinding.ItemListCardSelectableBinding;
import com.sxt.mvvm.data.card.CardMessage;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class CardSelectableAdapter extends BaseRecyclerAdapter<CardMessage> {

    public CardSelectableAdapter(Context context, List<CardMessage> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_list_card_selectable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        CardMessage card = getItem(position);
        holder.binding.setTitle(card.getTitle());
        holder.binding.setMessage(card.getMessage());
        MaterialCardView cardView = holder.binding.cardView;
        cardView.setOnLongClickListener(v -> {
            cardView.setChecked(!cardView.isChecked());
            return true;
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListCardSelectableBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}