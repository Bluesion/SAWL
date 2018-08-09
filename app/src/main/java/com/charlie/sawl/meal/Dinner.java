package com.charlie.sawl.meal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import com.charlie.sawl.meal.adapters.DinnerAdapter;
import com.charlie.sawl.tools.Preference;
import com.charlie.sawl.tools.Tools;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import java.util.Objects;

public class Dinner extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DinnerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    Calendar mCalendar;
    int YEAR, MONTH, DAY, DAY_OF_WEEK;
    boolean isUpdating = false;
    MealDownloadTask mProcessTask;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal, container, false);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = rootView.findViewById(R.id.recycler);
        mAdapter = new DinnerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(horizontalLayoutManager);

        SpeedDialOverlayLayout overlayLayout = rootView.findViewById(R.id.overlay);
        SpeedDialView speedDialView = rootView.findViewById(R.id.speedDial);
        speedDialView.inflate(R.menu.menu_meal);
        speedDialView.setOverlayLayout(overlayLayout);
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.refresh:
                        if (Tools.isOnline(Objects.requireNonNull(getActivity()).getApplicationContext())) {
                            getCalendarInstance(false);
                            if (mCalendar == null)
                                mCalendar = Calendar.getInstance();

                            int DayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
                            mCalendar.add(Calendar.DATE, 2 - DayOfWeek);

                            int year = mCalendar.get(Calendar.YEAR);
                            int month = mCalendar.get(Calendar.MONTH);
                            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                            String mPrefBreakfastName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_BREAKFAST);
                            String mPrefLunchName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_LUNCH);
                            String mPrefDinnerName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_DINNER);

                            Preference mPref = new Preference(getActivity().getApplicationContext(), MealTool.MEAL_PREFERENCE_NAME);
                            mPref.remove(mPrefBreakfastName);
                            mPref.remove(mPrefLunchName);
                            mPref.remove(mPrefDinnerName);

                            getMealList(true);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("새로고침 실패");
                            builder.setMessage("네트워크에 연결 후 다시 시도하세요");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.show();
                        }
                        return false;
                    case R.id.date:
                        Calendar now = Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                Dinner.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dpd.setYearRange(2014, 2036);
                        dpd.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "Illusion");
                        return false;
                    case R.id.alergy:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                        builder.setTitle("알레르기 정보");
                        builder.setMessage(R.string.alergy_text);
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.show();
                        return false;
                }
                return false;
            }
        });

        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCalendarInstance(true);
                getMealList(true);
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_red, R.color.refresh_green,
                R.color.refresh_purple, R.color.refresh_blue);

        getMealList(true);

        return rootView;
    }

    private void getCalendarInstance(boolean getInstance) {
        if (getInstance || (mCalendar == null))
            mCalendar = Calendar.getInstance();
        YEAR = mCalendar.get(Calendar.YEAR);
        MONTH = mCalendar.get(Calendar.MONTH);
        DAY = mCalendar.get(Calendar.DAY_OF_MONTH);
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
    }

    private void getMealList(boolean isUpdate) {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();
        getCalendarInstance(false);

        final Calendar mToday = Calendar.getInstance();
        final int TodayYear = mToday.get(Calendar.YEAR);
        final int TodayMonth = mToday.get(Calendar.MONTH);
        final int TodayDay = mToday.get(Calendar.DAY_OF_MONTH);

        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
        mCalendar.add(Calendar.DATE, 2 - DAY_OF_WEEK);

        for (int i = 0; i < 7; i++) {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            MealTool.restoreMealDateClass mData = MealTool.restoreMealData(getActivity(), year, month, day);

            if (mData.isBlankDay) {
                if (isUpdate) {
                    if (!isUpdating) {
                        mSwipeRefreshLayout.setRefreshing(true);

                        mProcessTask = new MealDownloadTask(getActivity());
                        mProcessTask.execute(year, month, day);

                        isUpdating = true;
                    }
                }

                return;
            }

            if ((year == TodayYear) && (month == TodayMonth) && (day == TodayDay)) {
                mAdapter.addItem(mData.Calender, mData.DayOfTheWeek, mData.Breakfast, mData.Lunch, mData.Dinner, true);
            } else {
                mAdapter.addItem(mData.Calender, mData.DayOfTheWeek, mData.Breakfast, mData.Lunch, mData.Dinner, false);
            }
            mCalendar.add(Calendar.DATE, 1);
        }

        mCalendar.set(YEAR, MONTH, DAY);
        mAdapter.notifyDataSetChanged();
        setCurrentItem();
    }

    private void setCurrentItem() {
        if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            mRecyclerView.smoothScrollToPosition(0);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            mRecyclerView.smoothScrollToPosition(1);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            mRecyclerView.smoothScrollToPosition(2);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            mRecyclerView.smoothScrollToPosition(3);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            mRecyclerView.smoothScrollToPosition(4);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            mRecyclerView.smoothScrollToPosition(5);
        } else {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private class MealDownloadTask extends ProcessTask {
        private MealDownloadTask(Context mContext) {
            super(mContext);
        }

        @Override
        public void onPreDownload() {}

        @Override
        public void onUpdate(int progress) {
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onFinish(long result) {
            mSwipeRefreshLayout.setRefreshing(false);
            isUpdating = false;

            getMealList(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mSwipeRefreshLayout.setRefreshing(false);
        mCalendar = null;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        mCalendar.set(year, month, day);
        YEAR = year;
        MONTH = month;
        DAY = day;
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
        getMealList(true);
    }
}