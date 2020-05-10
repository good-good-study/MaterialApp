package com.sxt.mvvm.base;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun on 2017/10/12.
 */
public class BaseFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter {
    private String[] titles;
    private FragmentManager fm;
    private Context context;
    private List<Fragment> fragments;
    private List<T> childData;

    public BaseFragmentStatePagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments, String... titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fm = fm;
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles[position] : null;
    }
}
