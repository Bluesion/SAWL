package com.charlie.sawl.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;

public class Main extends Fragment {

    private final String[] pageTitle = {"오늘의 급식", "오늘의 시간표"};
    ViewPager mViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        TabLayout tabLayout = rootView.findViewById(R.id.mTabLayout);
        int numOfPages = 2;
        for (int i = 0; i < numOfPages; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = rootView.findViewById(R.id.mViewPager);
        final PagerAdapter adapter = new Main.ContentsAdapter(getFragmentManager(), numOfPages);

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager));

        return rootView;
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
    }

    public class ContentsAdapter extends FragmentStatePagerAdapter {

        int numOfTabs;

        ContentsAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new TodayMeal();
                case 1:
                    return new TodayTimetable();
            }
            return null;
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}