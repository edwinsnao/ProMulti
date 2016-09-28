package com.example.king.fragement.main.mywork.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.king.fragement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/5/4.
 */
public class FragmentAdapter extends FragmentPagerAdapter
//public class FragmentAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider
{
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentsTitle = new ArrayList<>();
    private final int[] icons = {R.drawable.ic_menu, R.drawable.ic_menu
            ,R.drawable.ic_menu};
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragment, @Nullable List<String> titles) {
        super(fm);
        mFragments = fragment;
        mFragmentsTitle = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mFragmentsTitle.get(position)==null)
            return "";
        return mFragmentsTitle.get(position);
    }

//    @Override
//    public int getPageIconResId(int position) {
//        return icons[position];
//    }
}
