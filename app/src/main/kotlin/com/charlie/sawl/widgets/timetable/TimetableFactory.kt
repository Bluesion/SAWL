package com.charlie.sawl.widgets.timetable

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.charlie.sawl.R
import java.util.ArrayList
import java.util.Calendar

class TimetableFactory internal constructor(private val mContext: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private val items = ArrayList<String>()

    override fun onCreate() {
        val subjects = mContext.getSharedPreferences("timetable", Activity.MODE_PRIVATE)
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            items.add(subjects.getString("monday1", "")!!)
            items.add(subjects.getString("monday2", "")!!)
            items.add(subjects.getString("monday3", "")!!)
            items.add(subjects.getString("monday4", "")!!)
            items.add(subjects.getString("monday5", "")!!)
            items.add(subjects.getString("monday6", "")!!)
            items.add(subjects.getString("monday7", "")!!)
            items.add(subjects.getString("monday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            items.add(subjects.getString("tuesday1", "")!!)
            items.add(subjects.getString("tuesday2", "")!!)
            items.add(subjects.getString("tuesday3", "")!!)
            items.add(subjects.getString("tuesday4", "")!!)
            items.add(subjects.getString("tuesday5", "")!!)
            items.add(subjects.getString("tuesday6", "")!!)
            items.add(subjects.getString("tuesday7", "")!!)
            items.add(subjects.getString("tuesday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            items.add(subjects.getString("wednesday1", "")!!)
            items.add(subjects.getString("wednesday2", "")!!)
            items.add(subjects.getString("wednesday3", "")!!)
            items.add(subjects.getString("wednesday4", "")!!)
            items.add(subjects.getString("wednesday5", "")!!)
            items.add(subjects.getString("wednesday6", "")!!)
            items.add(subjects.getString("wednesday7", "")!!)
            items.add(subjects.getString("wednesday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            items.add(subjects.getString("thursday1", "")!!)
            items.add(subjects.getString("thursday2", "")!!)
            items.add(subjects.getString("thursday3", "")!!)
            items.add(subjects.getString("thursday4", "")!!)
            items.add(subjects.getString("thursday5", "")!!)
            items.add(subjects.getString("thursday6", "")!!)
            items.add(subjects.getString("thursday7", "")!!)
            items.add(subjects.getString("thursday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            items.add(subjects.getString("friday1", "")!!)
            items.add(subjects.getString("friday2", "")!!)
            items.add(subjects.getString("friday3", "")!!)
            items.add(subjects.getString("friday4", "")!!)
            items.add(subjects.getString("friday5", "")!!)
            items.add(subjects.getString("friday6", "")!!)
            items.add(subjects.getString("friday7", "")!!)
            items.add(subjects.getString("friday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            items.add("일정이 없습니다")
        }
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

        extras.putString(TimetableWidget.EXTRA_WORD, items[position])
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

        val subjects = mContext.getSharedPreferences("timetable", Activity.MODE_PRIVATE)
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            items.add(subjects.getString("monday1", "")!!)
            items.add(subjects.getString("monday2", "")!!)
            items.add(subjects.getString("monday3", "")!!)
            items.add(subjects.getString("monday4", "")!!)
            items.add(subjects.getString("monday5", "")!!)
            items.add(subjects.getString("monday6", "")!!)
            items.add(subjects.getString("monday7", "")!!)
            items.add(subjects.getString("monday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            items.add(subjects.getString("tuesday1", "")!!)
            items.add(subjects.getString("tuesday2", "")!!)
            items.add(subjects.getString("tuesday3", "")!!)
            items.add(subjects.getString("tuesday4", "")!!)
            items.add(subjects.getString("tuesday5", "")!!)
            items.add(subjects.getString("tuesday6", "")!!)
            items.add(subjects.getString("tuesday7", "")!!)
            items.add(subjects.getString("tuesday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            items.add(subjects.getString("wednesday1", "")!!)
            items.add(subjects.getString("wednesday2", "")!!)
            items.add(subjects.getString("wednesday3", "")!!)
            items.add(subjects.getString("wednesday4", "")!!)
            items.add(subjects.getString("wednesday5", "")!!)
            items.add(subjects.getString("wednesday6", "")!!)
            items.add(subjects.getString("wednesday7", "")!!)
            items.add(subjects.getString("wednesday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            items.add(subjects.getString("thursday1", "")!!)
            items.add(subjects.getString("thursday2", "")!!)
            items.add(subjects.getString("thursday3", "")!!)
            items.add(subjects.getString("thursday4", "")!!)
            items.add(subjects.getString("thursday5", "")!!)
            items.add(subjects.getString("thursday6", "")!!)
            items.add(subjects.getString("thursday7", "")!!)
            items.add(subjects.getString("thursday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            items.add(subjects.getString("friday1", "")!!)
            items.add(subjects.getString("friday2", "")!!)
            items.add(subjects.getString("friday3", "")!!)
            items.add(subjects.getString("friday4", "")!!)
            items.add(subjects.getString("friday5", "")!!)
            items.add(subjects.getString("friday6", "")!!)
            items.add(subjects.getString("friday7", "")!!)
            items.add(subjects.getString("friday8", "")!!)
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            items.add("일정이 없습니다")
        }
    }
}