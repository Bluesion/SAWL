package com.charlie.sawl.widgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.charlie.sawl.BootReceiver;
import com.charlie.sawl.MainActivity;
import com.charlie.sawl.R;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CounterWidget extends AppWidgetProvider {

    public static String ACTION_AUTO_UPDATE_WIDGET = "ACTION_AUTO_UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_counter);
        views.setTextViewText(R.id.countdown, getDDayString());

        PendingIntent layoutPendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        views.setOnClickPendingIntent(R.id.countdown, layoutPendingIntent);

        Intent mIntent = new Intent(context, BootReceiver.class);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
        views.setOnClickPendingIntent(R.id.countdown, updatePendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        super.onReceive(mContext, mIntent);

        if (ACTION_AUTO_UPDATE_WIDGET.equals(mIntent.getAction())) {
            updateCounter(mContext);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(CounterWidget.ACTION_AUTO_UPDATE_WIDGET);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 1);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmMgr).setInexactRepeating(AlarmManager.RTC, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        Intent intent = new Intent(CounterWidget.ACTION_AUTO_UPDATE_WIDGET);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmMgr).cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
    }

    public static void updateCounter(Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, CounterWidget.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(mContext, appWidgetManager, appWidgetId);
        }
    }

    static private String getDDayString() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(2018, Calendar.NOVEMBER, 15, 0, 0, 0);

        int diffDays = (int) ((mCalendar.getTimeInMillis() - System.currentTimeMillis()) / (86400000));

        return String.format(Locale.KOREA, "%d", diffDays+1);
    }
}