/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sxt.mvvm.base;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by sxt on 2017/10/3.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    protected Context context;
    private List<T> data;
    private ContentObserver contentObserver;
    protected OnItemClickListener<T> onItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerAdapter(Context context, List<T> data) {
        this(context);
        this.data = data;
        if (this.data == null || this.data.size() == 0) {
            if (contentObserver != null) {
                contentObserver.notify(0);
            }
        }
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    @Override
    public int getItemCount() {
        int count = data == null ? 0 : data.size();
        if (contentObserver != null) {
            contentObserver.notify(count);
        }
        return count;
    }

    public T getItem(int position) {
        if (data != null && data.size() > position) {
            return data.get(position);
        }
        return null;
    }

    public void notifyDataSetChanged(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<T> data, boolean isRefresh) {
        if (isRefresh) {
            if (data != null) {
                this.data = data;
            }
            notifyDataSetChanged();
        } else {
            if (this.data == null && data != null) {
                this.data = data;
                notifyDataSetChanged();
            } else {
                if (data != null) {
                    int old = getItemCount();//？这里为什么不使用this.data.size() ? 因为子类可能会重写 getItemCount 方法 ，会导致索引不准确
                    this.data.addAll(data);
                    notifyItemRangeInserted(old, data.size());
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onClick(int position, T t);
    }

    public void setContentObserver(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
    }

    public interface ContentObserver {
        void notify(int count);
    }
}