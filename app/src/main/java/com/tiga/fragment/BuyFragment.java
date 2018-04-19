package com.tiga.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiga.agent.R;
import com.tiga.utils.ViewPagerAdapter;

public class BuyFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";
    private ViewPager viewPager;
    private TabLayout tabLayout;


    public static BuyFragment createFor(String text) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        setupViewPager();
        tabLayout = (TabLayout) v.findViewById(R.id.tab_buy);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    public void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        BuyContentFragment buyContentFragment = new BuyContentFragment();
        viewPagerAdapter.addFragment(buyContentFragment, getString(R.string.beli_subsidi));

        buyContentFragment = new BuyContentFragment();
        viewPagerAdapter.addFragment(buyContentFragment, getString(R.string.beli_non_subsidi));

        viewPager.setAdapter(viewPagerAdapter);
    }
}
