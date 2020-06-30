package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemListImageBinding;
import com.sxt.mvvm.model.card.CardMessage;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/6/01
 */
public class ImageListAdapter extends BaseRecyclerAdapter<CardMessage> {

    public ImageListAdapter(Context context, List<CardMessage> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_list_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.binding.setCard(getItem(position));
        holder.binding.executePendingBindings();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListImageBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}