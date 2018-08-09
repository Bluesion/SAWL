package com.charlie.sawl.meal;

import android.content.Context;
import com.charlie.sawl.R;
import com.charlie.sawl.tools.Preference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MealTool {
    public static final String MEAL_PREFERENCE_NAME = "MealData";
    public static final int TYPE_BREAKFAST = 1, TYPE_LUNCH = 2, TYPE_DINNER = 3;

    public static String getMealStringFormat(int year, int month, int day, int type) {
        month += 1;
        return year + "-" + month + "-" + day + "-" + type;
    }

    public static void saveMealData(Context mContext, String[] Calender, String[] Breakfast, String[] Lunch, String[] Dinner) {
        Preference mPref = new Preference(mContext, MEAL_PREFERENCE_NAME);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREA);

        for (int index = 0; index < Calender.length; index++) {
            try {
                Calendar mDate = Calendar.getInstance();
                mDate.setTime(mFormat.parse(Calender[index]));

                int year = mDate.get(Calendar.YEAR);
                int month = mDate.get(Calendar.MONTH) + 1;
                int day = mDate.get(Calendar.DAY_OF_MONTH);

                String mPrefBreakfastName = getMealStringFormat(year, month, day, TYPE_BREAKFAST);
                String mPrefLunchName = getMealStringFormat(year, month, day, TYPE_LUNCH);
                String mPrefDinnerName = getMealStringFormat(year, month, day, TYPE_DINNER);

                String mBreakfast = Breakfast[index];
                String mLunch = Lunch[index];
                String mDinner = Dinner[index];

                if (MealLibrary.isMealCheck(mBreakfast)) mBreakfast = "";
                if (MealLibrary.isMealCheck(mLunch)) mLunch = "";
                if (MealLibrary.isMealCheck(mDinner)) mDinner = "";

                mPref.putString(mPrefBreakfastName, mBreakfast);
                mPref.putString(mPrefLunchName, mLunch);
                mPref.putString(mPrefDinnerName, mDinner);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static restoreMealDateClass restoreMealData(Context mContext, int year, int month, int day) {
        Preference mPref = new Preference(mContext, MEAL_PREFERENCE_NAME);
        SimpleDateFormat mCalenderFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        SimpleDateFormat mDayOfWeekFormat = new SimpleDateFormat("E요일", Locale.KOREA);
        Calendar mDate = Calendar.getInstance();
        mDate.set(year, month, day);

        restoreMealDateClass mData = new restoreMealDateClass();

        String mPrefBreakfastName = getMealStringFormat(year, month + 1, day, TYPE_BREAKFAST);
        String mPrefLunchName = getMealStringFormat(year, month + 1, day, TYPE_LUNCH);
        String mPrefDinnerName = getMealStringFormat(year, month + 1, day, TYPE_DINNER);

        mData.Calender = mCalenderFormat.format(mDate.getTime());
        mData.DayOfTheWeek = mDayOfWeekFormat.format(mDate.getTime());
        mData.Breakfast = mPref.getString(mPrefBreakfastName, null);
        mData.Lunch = mPref.getString(mPrefLunchName, null);
        mData.Dinner = mPref.getString(mPrefDinnerName, null);

        if (mData.Breakfast == null && mData.Lunch == null && mData.Dinner == null) {
            mData.isBlankDay = true;
        }

        return mData;
    }

    static class restoreMealDateClass {
        String Calender, DayOfTheWeek, Breakfast, Lunch, Dinner;
        boolean isBlankDay = false;
    }

    public static boolean mStringCheck(String mString) {
        return mString == null || "".equals(mString) || " ".equals(mString);
    }

    public static todayMealData getTodayMeal(Context mContext) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        restoreMealDateClass mData = MealTool.restoreMealData(mContext, year, month, day);
        todayMealData mReturnData = new todayMealData();

        if (!mData.isBlankDay) {
            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 24 || hour <= 8) {
                mReturnData.title = mContext.getString(R.string.today_breakfast);
                mReturnData.info = (MealLibrary.isMealCheck(mData.Breakfast) ? mContext.getString(R.string.no_data_breakfast) : mData.Breakfast);
            } else if (hour < 14) {
                mReturnData.title = mContext.getString(R.string.today_lunch);
                mReturnData.info = (MealLibrary.isMealCheck(mData.Lunch) ? mContext.getString(R.string.no_data_lunch) : mData.Lunch);
            } else {
                mReturnData.title = mContext.getString(R.string.today_dinner);
                mReturnData.info = (MealLibrary.isMealCheck(mData.Dinner) ? mContext.getString(R.string.no_data_dinner) : mData.Dinner);
            }
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title);
            mReturnData.info = mContext.getString(R.string.no_data_message);
        }

        return mReturnData;
    }

    public static todayMealData todayBreakfast(Context mContext) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        restoreMealDateClass mData = MealTool.restoreMealData(mContext, year, month, day);
        todayMealData mReturnData = new todayMealData();

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message);
            mReturnData.info = (MealLibrary.isMealCheck(mData.Breakfast) ? mContext.getString(R.string.no_data_message) : mData.Breakfast);
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title);
            mReturnData.info = mContext.getString(R.string.no_data_message);
        }

        return mReturnData;
    }

    public static todayMealData todayLunch(Context mContext) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        restoreMealDateClass mData = MealTool.restoreMealData(mContext, year, month, day);
        todayMealData mReturnData = new todayMealData();

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message);
            mReturnData.info = (MealLibrary.isMealCheck(mData.Lunch) ? mContext.getString(R.string.no_data_message) : mData.Lunch);
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title);
            mReturnData.info = mContext.getString(R.string.no_data_message);
        }

        return mReturnData;
    }

    public static todayMealData todayDinner(Context mContext) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        restoreMealDateClass mData = MealTool.restoreMealData(mContext, year, month, day);
        todayMealData mReturnData = new todayMealData();

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message);
            mReturnData.info = (MealLibrary.isMealCheck(mData.Dinner) ? mContext.getString(R.string.no_data_message) : mData.Dinner);
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title);
            mReturnData.info = mContext.getString(R.string.no_data_message);
        }

        return mReturnData;
    }

    public static class todayMealData {
        public String title, info;
    }
}