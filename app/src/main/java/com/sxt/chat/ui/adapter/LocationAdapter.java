package com.sxt.chat.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sxt.chat.R;
import com.sxt.chat.databinding.ItemLocationBinding;
import com.sxt.mvvm.model.location.LocationInfo;
import com.sxt.mvvm.view.BaseRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LocationAdapter extends BaseRecyclerAdapter<LocationInfo> {
    private int index = -1;

    public LocationAdapter(Context context, List<LocationInfo> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        LocationInfo location = getItem(position);
        holder.binding.title.setText(getAddressTitle(position, location));
        holder.binding.subTitle.setText(location.getAddress());
        holder.binding.distance.setText(getDistance(location));
        if (index == position) {
            holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.main_blue));
            holder.binding.subTitle.setTextColor(ContextCompat.getColor(context, R.color.main_blue));
        } else {
            holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.text_color_1));
            holder.binding.subTitle.setTextColor(ContextCompat.getColor(context, R.color.text_color_1));
        }
        holder.itemView.setOnClickListener(v -> {
            this.index = position;
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position, location);
            }
        });
    }

    @NotNull
    private String getDistance(LocationInfo location) {
        return String.format("%s km", location.getDistance());
    }

    @NotNull
    private String getAddressTitle(int position, LocationInfo location) {
        return String.format("%s, %s", position + 1, location.getAddressName());
    }

    public LocationAdapter resetIndex() {
        this.index = -1;
        return this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemLocationBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}