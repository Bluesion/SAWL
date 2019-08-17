package com.charlie.sawl.widgets

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.charlie.sawl.BootReceiver
import com.charlie.sawl.MainActivity
import com.charlie.sawl.R
import java.util.Calendar
import java.util.Objects

class CounterWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(mContext: Context, mIntent: Intent) {
        super.onReceive(mContext, mIntent)

        if (ACTION_AUTO_UPDATE_WIDGET == mIntent.action) {
            updateCounter(mContext)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        val intent = Intent(CounterWidget.ACTION_AUTO_UPDATE_WIDGET)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 1)

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Objects.requireNonNull(alarmMgr).setInexactRepeating(AlarmManager.RTC, c.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntent)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)

        val intent = Intent(CounterWidget.ACTION_AUTO_UPDATE_WIDGET)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Objects.requireNonNull(alarmMgr).cancel(PendingIntent.getBroadcast(context, 0, intent, 0))
    }

    companion object {
        var ACTION_AUTO_UPDATE_WIDGET = "ACTION_AUTO_UPDATE_WIDGET"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_counter)
            views.setTextViewText(R.id.countdown, dDayString)

            val layoutPendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
            views.setOnClickPendingIntent(R.id.countdown, layoutPendingIntent)

            val mIntent = Intent(context, BootReceiver::class.java)
            val updatePendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0)
            views.setOnClickPendingIntent(R.id.countdown, updatePendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun updateCounter(mContext: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(mContext)

            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(mContext, CounterWidget::class.java))

            for (appWidgetId in appWidgetIds) {
                updateAppWidget(mContext, appWidgetManager, appWidgetId)
            }
        }

        private val dDayString: String
            get() {
                val mCalendar = Calendar.getInstance()
                mCalendar.set(2019, Calendar.NOVEMBER, 14, 0, 0, 0)

                val diffDays = ((mCalendar.timeInMillis - System.currentTimeMillis()) / 86400000).toInt()
                return if (diffDays > 0) {
                    "-$diffDays"
                } else {
                    "+" + -diffDays
                }
            }
    }
}