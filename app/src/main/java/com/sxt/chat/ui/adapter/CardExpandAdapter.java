package com.sxt.chat.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.google.android.material.internal.ViewUtils;
import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemCardExpandBinding;
import com.sxt.chat.ui.adapter.listener.EventListener;
import com.sxt.chat.utils.AnimationUtil;
import com.sxt.chat.view.transition.FadeInTransition;
import com.sxt.chat.view.transition.FadeOutTransition;
import com.sxt.mvvm.data.card.CardComment;
import com.sxt.mvvm.data.material.Category;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Created by xt.sun
 * 2020/5/31
 */
public class CardExpandAdapter extends BaseRecyclerAdapter<CardComment> {
    private EventListener listener;

    public CardExpandAdapter(Context context, List<CardComment> data) {
        super(context, data);
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_card_expand, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        bindAdapter(position, holder);
    }

    private void bindAdapter(int position, ViewHolder holder) {
        holder.binding.setCardComment(getItem(position));
        //设置扩展项数据
        RecyclerView recyclerView = holder.binding.content;
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(context, R.drawable.divider);
        if (divider != null) {
            decoration.setDrawable(divider);
        }
        CommentAdapter adapter = new CommentAdapter(context, getItem(position).getComments());
        recyclerView.setAdapter(adapter);
        //expand、collapse
        holder.binding.arrow.setOnClickListener(v -> onEvent(holder));
        holder.binding.executePendingBindings();
    }

    private void onEvent(ViewHolder holder) {
        RecyclerView content = holder.binding.content;
        int visibility = content.getVisibility();
        int startAngle, endAngle;
        if (visibility == View.VISIBLE) {
            holder.binding.divider.setVisibility(View.INVISIBLE);
            visibility = View.GONE;
            startAngle = 180;
            endAngle = 0;
            if (listener != null) {
                listener.onCollapse();
            }
        } else {
            holder.binding.divider.setVisibility(View.VISIBLE);
            visibility = View.VISIBLE;
            startAngle = 0;
            endAngle = 180;
            if (listener != null) {
                listener.onExpand();
            }
        }
        content.setVisibility(visibility);
        AnimationUtil.rotation(holder.binding.arrow, startAngle, endAngle);
    }

    @NotNull
    private Transition getExpandTransition() {
        FadeInTransition transition = new FadeInTransition();
        transition.setDuration(200);
        return transition;
    }

    @NotNull
    private Transition getCollapseTransition() {
        FadeOutTransition transition = new FadeOutTransition();
        transition.setDuration(200);
        return transition;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardExpandBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}