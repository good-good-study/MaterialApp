package com.sxt.chat.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun on 2017/10/12.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private String[] titles;
    private Context context;
    private List<Fragment> fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments, String... titles) {
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
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }

    public void notifyDataSetChanged(List<Fragment> fragments) {
        if (fragments != null) {
            this.fragments = fragments;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.fragments != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            for (int i = 0; i < this.fragments.size(); i++) {
                transaction.remove(this.fragments.get(i));
            }
            transaction.commit();
            fm.executePendingTransactions();
        }
        super.notifyDataSetChanged();
    }
}
