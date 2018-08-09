package com.charlie.sawl.timetable;

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
import java.util.Calendar;

public class TimeTable extends Fragment {

    private final String[] pageTitle = {"월요일", "화요일", "수요일", "목요일", "금요일"};
    ViewPager mViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        TabLayout tabLayout = rootView.findViewById(R.id.mTabLayout);
        int numOfPages = 5;
        for (int i = 0; i < numOfPages; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = rootView.findViewById(R.id.mViewPager);
        final PagerAdapter adapter = new TimeTableAdapter(getFragmentManager(), numOfPages);

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager));
        setCurrentItem();

        return rootView;
    }

    private void setCurrentItem() {
        Calendar mCalendar = Calendar.getInstance();

        if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            mViewPager.setCurrentItem(0);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            mViewPager.setCurrentItem(1);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            mViewPager.setCurrentItem(2);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            mViewPager.setCurrentItem(3);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            mViewPager.setCurrentItem(4);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            mViewPager.setCurrentItem(0);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            mViewPager.setCurrentItem(0);
        }
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

    public class TimeTableAdapter extends FragmentStatePagerAdapter {

        int numOfTabs;

        TimeTableAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new Monday();
                case 1:
                    return new Tuesday();
                case 2:
                    return new Wednesday();
                case 3:
                    return new Thursday();
                case 4:
                    return new Friday();
            }

            return null;
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}