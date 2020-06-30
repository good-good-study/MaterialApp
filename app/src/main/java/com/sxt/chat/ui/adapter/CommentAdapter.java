package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemCommentBinding;
import com.sxt.mvvm.model.card.Comment;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class CommentAdapter extends BaseRecyclerAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.binding.setComment(getItem(position));
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position, getItem(position));
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}