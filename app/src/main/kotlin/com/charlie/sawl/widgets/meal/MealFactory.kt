package com.charlie.sawl.widgets.meal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.charlie.sawl.R
import com.charlie.sawl.meal.MealTool
import java.util.ArrayList

class MealFactory internal constructor(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val items = ArrayList<String?>()

    override fun onCreate() {
        val mBreakfastData = MealTool.todayBreakfast(mContext)
        val mLunchData = MealTool.todayLunch(mContext)
        val mDinnerData = MealTool.todayDinner(mContext)
        val mBreakfast = mBreakfastData.info
        val mLunch = mLunchData.info
        val mDinner = mDinnerData.info

        items.add("아침")
        items.add(mBreakfast)
        items.add("점심")
        items.add(mLunch)
        items.add("저녁")
        items.add(mDinner)
    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return items.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val row = RemoteViews(mContext.packageName, R.layout.row_widget_item)
        row.setTextViewText(android.R.id.text1, items[position])

        val i = Intent()
        val extras = Bundle()

        extras.putString(MealWidget.EXTRA_WORD, items[position])
        i.putExtras(extras)
        row.setOnClickFillInIntent(android.R.id.text1, i)

        return row
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        items.clear()

        val mBreakfastData = MealTool.todayBreakfast(mContext)
        val mLunchData = MealTool.todayLunch(mContext)
        val mDinnerData = MealTool.todayDinner(mContext)
        val mBreakfast = mBreakfastData.info
        val mLunch = mLunchData.info
        val mDinner = mDinnerData.info

        items.add("아침")
        items.add(mBreakfast)
        items.add("점심")
        items.add(mLunch)
        items.add("저녁")
        items.add(mDinner)
    }
}