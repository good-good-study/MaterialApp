package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemMaterialListBinding;
import com.sxt.chat.ui.adapter.listener.EventListener;
import com.sxt.chat.utils.AnimationUtil;
import com.sxt.mvvm.data.material.Category;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class MaterialAdapter extends BaseRecyclerAdapter<Category> {
    private EventListener listener;

    public MaterialAdapter(Context context, List<Category> data) {
        super(context, data);
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_material_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        bindAdapter(position, holder);
    }

    private void bindAdapter(int position, ViewHolder holder) {
        Category category = getItem(position);
        holder.binding.setCategory(category);
        holder.binding.content.setNestedScrollingEnabled(false);
        //设置扩展项数据
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(context, R.drawable.divider);
        if (divider != null) {
            decoration.setDrawable(divider);
        }
        holder.binding.content.addItemDecoration(decoration);
        ItemAdapter adapter = new ItemAdapter(context, category.getTag());
        holder.binding.content.setAdapter(adapter);
        adapter.setOnItemClickListener((index, option) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(index, category);
            }
        });
        //expand、collapse
        holder.binding.cardView.setOnClickListener(v -> onEvent(holder));
        holder.binding.arrow.setOnClickListener(v -> onEvent(holder));
    }

    private void onEvent(ViewHolder holder) {
        int visibility = holder.binding.content.getVisibility();
        int startAngle, endAngle;
        if (visibility == View.VISIBLE) {
            visibility = View.GONE;
            startAngle = 180;
            endAngle = 0;
            if (listener != null) {
                listener.onCollapse();
            }
        } else {
            visibility = View.VISIBLE;
            startAngle = 0;
            endAngle = 180;
            if (listener != null) {
                listener.onExpand();
            }
        }
        holder.binding.content.setVisibility(visibility);
        AnimationUtil.rotation(holder.binding.arrow, startAngle, endAngle);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMaterialListBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}