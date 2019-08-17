package com.charlie.sawl.meal

import android.content.Context
import com.charlie.sawl.R
import com.charlie.sawl.tools.Preference
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object MealTool {
    const val MEAL_PREFERENCE_NAME = "MealData"
    const val TYPE_BREAKFAST = 1
    const val TYPE_LUNCH = 2
    const val TYPE_DINNER = 3

    fun getMealStringFormat(year: Int, month: Int, day: Int, type: Int): String {
        var month = month
        month += 1
        return "$year-$month-$day-$type"
    }

    fun saveMealData(mContext: Context, Calender: Array<String?>, Breakfast: Array<String?>, Lunch: Array<String?>, Dinner: Array<String?>) {
        val mPref = Preference(mContext, MEAL_PREFERENCE_NAME)
        val mFormat = SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREA)

        for (index in Calender.indices) {
            try {
                val mDate = Calendar.getInstance()
                mDate.time = mFormat.parse(Calender[index]!!)!!

                val year = mDate.get(Calendar.YEAR)
                val month = mDate.get(Calendar.MONTH) + 1
                val day = mDate.get(Calendar.DAY_OF_MONTH)

                val mPrefBreakfastName = getMealStringFormat(year, month, day, TYPE_BREAKFAST)
                val mPrefLunchName = getMealStringFormat(year, month, day, TYPE_LUNCH)
                val mPrefDinnerName = getMealStringFormat(year, month, day, TYPE_DINNER)

                var mBreakfast = Breakfast[index]!!.trim()
                var mLunch = Lunch[index]!!.trim()
                var mDinner = Dinner[index]!!.trim()

                if (MealLibrary.isMealCheck(mBreakfast)) mBreakfast = ""
                if (MealLibrary.isMealCheck(mLunch)) mLunch = ""
                if (MealLibrary.isMealCheck(mDinner)) mDinner = ""

                mPref.putString(mPrefBreakfastName, mBreakfast)
                mPref.putString(mPrefLunchName, mLunch)
                mPref.putString(mPrefDinnerName, mDinner)

            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    internal fun restoreMealData(mContext: Context, year: Int, month: Int, day: Int): RestoreMealDateClass {
        val mPref = Preference(mContext, MEAL_PREFERENCE_NAME)
        val mCalenderFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
        val mDayOfWeekFormat = SimpleDateFormat("E요일", Locale.KOREA)
        val mDate = Calendar.getInstance()
        mDate.set(year, month, day)

        val mData = RestoreMealDateClass()

        val mPrefBreakfastName = getMealStringFormat(year, month + 1, day, TYPE_BREAKFAST)
        val mPrefLunchName = getMealStringFormat(year, month + 1, day, TYPE_LUNCH)
        val mPrefDinnerName = getMealStringFormat(year, month + 1, day, TYPE_DINNER)

        mData.calendar = mCalenderFormat.format(mDate.time)
        mData.dayOfTheWeek = mDayOfWeekFormat.format(mDate.time)
        mData.breakfast = mPref.getString(mPrefBreakfastName, null)
        mData.lunch = mPref.getString(mPrefLunchName, null)
        mData.dinner = mPref.getString(mPrefDinnerName, null)

        if (mData.breakfast == null && mData.lunch == null && mData.dinner == null) {
            mData.isBlankDay = true
        }

        return mData
    }

    internal class RestoreMealDateClass {
        var calendar: String? = null
        var dayOfTheWeek: String? = null
        var breakfast: String? = null
        var lunch: String? = null
        var dinner: String? = null
        var isBlankDay = false
    }

    fun mStringCheck(mString: String?): Boolean {
        return mString == null || "" == mString || " " == mString || "null" == mString
    }

    fun todayBreakfast(mContext: Context): TodayMealData {
        val mCalendar = Calendar.getInstance()
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DAY_OF_MONTH)

        val mData = restoreMealData(mContext, year, month, day)
        val mReturnData = TodayMealData()

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message)
            mReturnData.info = if (MealLibrary.isMealCheck(mData.breakfast)) mContext.getString(R.string.no_data_message) else mData.breakfast
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title)
            mReturnData.info = mContext.getString(R.string.no_data_message)
        }

        return mReturnData
    }

    fun todayLunch(mContext: Context): TodayMealData {
        val mCalendar = Calendar.getInstance()
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DAY_OF_MONTH)

        val mData = restoreMealData(mContext, year, month, day)
        val mReturnData = TodayMealData()

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message)
            mReturnData.info = if (MealLibrary.isMealCheck(mData.lunch)) mContext.getString(R.string.no_data_message) else mData.lunch
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title)
            mReturnData.info = mContext.getString(R.string.no_data_message)
        }

        return mReturnData
    }

    fun todayDinner(mContext: Context): TodayMealData {
        val mCalendar = Calendar.getInstance()
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DAY_OF_MONTH)

        val mData = restoreMealData(mContext, year, month, day)
        val mReturnData = TodayMealData()

        if (!mData.isBlankDay) {
            mReturnData.title = mContext.getString(R.string.no_message)
            mReturnData.info = if (MealLibrary.isMealCheck(mData.dinner)) mContext.getString(R.string.no_data_message) else mData.dinner
        } else {
            mReturnData.title = mContext.getString(R.string.no_data_title)
            mReturnData.info = mContext.getString(R.string.no_data_message)
        }

        return mReturnData
    }

    class TodayMealData {
        var title: String? = null
        var info: String? = null
    }
}