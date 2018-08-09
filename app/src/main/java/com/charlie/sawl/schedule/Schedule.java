package com.charlie.sawl.schedule;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule extends Fragment {

    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        mViewPager = rootView.findViewById(R.id.mViewPager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = rootView.findViewById(R.id.mTabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setCurrentItem();

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter mAdapter = new Adapter(getActivity().getSupportFragmentManager());

        mAdapter.addFragment("3월", ScheduleContents.getInstance(3));
        mAdapter.addFragment("4월", ScheduleContents.getInstance(4));
        mAdapter.addFragment("5월", ScheduleContents.getInstance(5));
        mAdapter.addFragment("6월", ScheduleContents.getInstance(6));
        mAdapter.addFragment("7월", ScheduleContents.getInstance(7));
        mAdapter.addFragment("8월", ScheduleContents.getInstance(8));
        mAdapter.addFragment("9월", ScheduleContents.getInstance(9));
        mAdapter.addFragment("10월", ScheduleContents.getInstance(10));
        mAdapter.addFragment("11월", ScheduleContents.getInstance(11));
        mAdapter.addFragment("12월", ScheduleContents.getInstance(12));
        mAdapter.addFragment("2019년 1월", ScheduleContents.getInstance(1));
        mAdapter.addFragment("2019년 2월", ScheduleContents.getInstance(2));

        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("공지사항");
                builder.setMessage("1. 본 학사일정은 학교 홈페이지에 기초하여 작성되었으며, 일정은 학교 사정에 의해 언제든지 변경될 수 있습니다.\n" +
                        "\n" +
                        "2. 재학생들에게 도움이 될 만한 일정만 골라서 작성되었으므로 일부 일정은 빠져있습니다.\n" +
                        "\n" +
                        "3. 퇴사주 금요일만 본 학사일정에 작성하였습니다. 퇴사로 표시되어 있지 않은 날은 모두 잔류이며, 퇴사 일정에 대한 정확한 정보는 꼭 사감실에서 확인 바랍니다.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
                break;
        }
        return true;
    }

    private void setCurrentItem() {
        int month = Calendar.getInstance().get(Calendar.MONTH);

        if (month >= 2) month -= 2;
        else month += 9;

        mViewPager.setCurrentItem(month);
    }

    private class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        void addFragment(String mTitle, Fragment mFragment) {
            mFragments.add(mFragment);
            mFragmentTitles.add(mTitle);
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
            return mFragmentTitles.get(position);
        }
    }
}