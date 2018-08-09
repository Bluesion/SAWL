package com.charlie.sawl.widgets.meal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import com.charlie.sawl.MainActivity;
import com.charlie.sawl.R;
import java.util.Calendar;
import java.util.Objects;

public class MealWidget extends AppWidgetProvider {

    public static String EXTRA_WORD = "Meal", UPDATE = "UPDATE_WIDGET";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(context, MealWidgetService.class);

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_meal);
            widget.setTextColor(R.id.text, Color.parseColor("#013e7f"));
            widget.setRemoteAdapter(R.id.meals, svcIntent);

            if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                widget.setTextViewText(R.id.text, "월요일 급식");
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                widget.setTextViewText(R.id.text, "화요일 급식");
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                widget.setTextViewText(R.id.text, "수요일 급식");
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                widget.setTextViewText(R.id.text, "목요일 급식");
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                widget.setTextViewText(R.id.text, "금요일 급식");
            } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                widget.setTextViewText(R.id.text, "주말 급식");
            }

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.meals, clickPI);

            clickIntent = new Intent(context, MealWidget.class);
            clickIntent.setAction(UPDATE);
            PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context,0, clickIntent, 0);
            widget.setOnClickPendingIntent(R.id.refresh, pendingIntentRefresh);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        super.onReceive(mContext, mIntent);

        if (Objects.requireNonNull(mIntent.getAction()).equalsIgnoreCase(UPDATE)) {
            updateWidget(mContext);
        }
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, MealWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.meals);
    }
}

