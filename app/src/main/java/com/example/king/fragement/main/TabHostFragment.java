package com.example.king.fragement.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.king.fragement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/3/9.
 */
public class TabHostFragment extends Fragment {
    public static FragmentTabHost mTabHost;
    LayoutInflater mLayoutInflater;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTabHost = new FragmentTabHost(getActivity());
//        mTabHost.setup(getActivity(),getChildFragmentManager(), R.id.fragment);
        mTabHost.getTabWidget().setDividerDrawable(null);
//        mTabHost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAllFragment(transaction);
//            }
//        });

        TabHost.TabSpec[] tabSpecs = new TabHost.TabSpec[3];
        String[] texts = new String[3];
        ChangeColorIconWithText[] tabviews = new ChangeColorIconWithText[3];

        texts[0] = getString(R.string.home);
        tabviews[0] = getTabView(R.layout.item_tab_discovery);
        tabSpecs[0] = mTabHost.newTabSpec(texts[0]).setIndicator(tabviews[0]);
        mTabHost.addTab(tabSpecs[0], HomeFragment1.class, null);
        mTabIndicators.add(tabviews[0]);

        texts[1] = getString(R.string.search_news1);
        tabviews[1] = getTabView(R.layout.item_tab_allnodes);
        tabSpecs[1] = mTabHost.newTabSpec(texts[1]).setIndicator(tabviews[1]);
        mTabHost.addTab(tabSpecs[1], SearchMideaFragment.class, null);
        mTabIndicators.add(tabviews[1]);

        texts[2] = getString(R.string.personal);
        tabviews[2] = getTabView(R.layout.item_tab_myinfo);
        tabSpecs[2] = mTabHost.newTabSpec(texts[2]).setIndicator(tabviews[2]);
        mTabHost.addTab(tabSpecs[2], PersonFragment.class, null);
        mTabIndicators.add(tabviews[2]);

        mTabHost.setOnTabChangedListener((TabHost.OnTabChangeListener) getActivity());
        mTabHost.setCurrentTab(0);
        tabviews[0].setIconAlpha(1.0f);
//        setTitle(texts[0]);

        return mTabHost;
    }
    private ChangeColorIconWithText getTabView(int layoutId) {
        ChangeColorIconWithText tab = (ChangeColorIconWithText) mLayoutInflater.inflate(layoutId, null);
        return tab;
    }
}
