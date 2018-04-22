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
    private static final boolean SUBSIDI = true;
    private static final boolean NON_SUBSIDI = false;
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
        tabLayout = (TabLayout) v.findViewById(R.id.tab_buy);

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        BuyContentFragment buyContentFragment = new BuyContentFragment();
        buyContentFragment.setSubsidiStatus(SUBSIDI);
        viewPagerAdapter.addFragment(buyContentFragment, getString(R.string.title_subsidi));

        BuyNonContentFragment buyNonContentFragment = new BuyNonContentFragment();
        buyNonContentFragment.setSubsidiStatus(NON_SUBSIDI);
        viewPagerAdapter.addFragment(buyNonContentFragment, getString(R.string.title_non_subsidi));

        viewPager.setAdapter(viewPagerAdapter);
    }
}
