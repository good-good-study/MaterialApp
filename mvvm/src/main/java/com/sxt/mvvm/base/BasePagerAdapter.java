package com.sxt.mvvm.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by xt.sun on 2017/7/17.
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected Context context;
    protected List<T> data;
    private String[] titles;

    protected BasePagerAdapter(Context context, List<T> data, String... titles) {
        this.context = context;
        this.data = data;
        this.titles = titles;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length == data.size()) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public abstract Object instantiateItem(@NonNull ViewGroup container, int position);
}
