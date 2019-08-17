package com.charlie.sawl.widgets.timetable

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import com.charlie.sawl.MainActivity
import com.charlie.sawl.R
import java.util.Calendar
import java.util.Objects

class TimetableWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val svcIntent = Intent(context, TimetableWidgetService::class.java)

            val widget = RemoteViews(context.packageName, R.layout.widget_timetable)
            widget.setTextColor(R.id.text, Color.parseColor("#013e7f"))
            widget.setRemoteAdapter(R.id.subjects, svcIntent)

            if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                widget.setTextViewText(R.id.text, "월요일 시간표")
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                widget.setTextViewText(R.id.text, "화요일 시간표")
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                widget.setTextViewText(R.id.text, "수요일 시간표")
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                widget.setTextViewText(R.id.text, "목요일 시간표")
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                widget.setTextViewText(R.id.text, "금요일 시간표")
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                widget.setTextViewText(R.id.text, "주말 시간표")
            }

            var clickIntent = Intent(context, MainActivity::class.java)
            val clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            widget.setPendingIntentTemplate(R.id.subjects, clickPI)

            clickIntent = Intent(context, TimetableWidget::class.java)
            clickIntent.action = UPDATE
            val pendingIntentRefresh = PendingIntent.getBroadcast(context, 0, clickIntent, 0)
            widget.setOnClickPendingIntent(R.id.refresh, pendingIntentRefresh)

            appWidgetManager.updateAppWidget(appWidgetId, widget)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(mContext: Context, mIntent: Intent) {
        super.onReceive(mContext, mIntent)

        if (Objects.requireNonNull(mIntent.action).equals(UPDATE, ignoreCase = true)) {
            updateWidget(mContext)
        }
    }

    companion object {
        var EXTRA_WORD = "TimeTable"
        var UPDATE = "UPDATE_WIDGET"

        fun updateWidget(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, TimetableWidget::class.java))
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.subjects)
        }
    }
}

