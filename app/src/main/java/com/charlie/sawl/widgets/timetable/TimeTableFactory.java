package com.charlie.sawl.widgets.timetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.charlie.sawl.R;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<String> items = new ArrayList<>();
    private Context mContext;

    TimeTableFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        SharedPreferences subjects = mContext.getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            items.add(subjects.getString("monday1", ""));
            items.add(subjects.getString("monday2", ""));
            items.add(subjects.getString("monday3", ""));
            items.add(subjects.getString("monday4", ""));
            items.add(subjects.getString("monday5", ""));
            items.add(subjects.getString("monday6", ""));
            items.add(subjects.getString("monday7", ""));
            items.add(subjects.getString("monday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            items.add(subjects.getString("tuesday1", ""));
            items.add(subjects.getString("tuesday2", ""));
            items.add(subjects.getString("tuesday3", ""));
            items.add(subjects.getString("tuesday4", ""));
            items.add(subjects.getString("tuesday5", ""));
            items.add(subjects.getString("tuesday6", ""));
            items.add(subjects.getString("tuesday7", ""));
            items.add(subjects.getString("tuesday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            items.add(subjects.getString("wednesday1", ""));
            items.add(subjects.getString("wednesday2", ""));
            items.add(subjects.getString("wednesday3", ""));
            items.add(subjects.getString("wednesday4", ""));
            items.add(subjects.getString("wednesday5", ""));
            items.add(subjects.getString("wednesday6", ""));
            items.add(subjects.getString("wednesday7", ""));
            items.add(subjects.getString("wednesday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            items.add(subjects.getString("thursday1", ""));
            items.add(subjects.getString("thursday2", ""));
            items.add(subjects.getString("thursday3", ""));
            items.add(subjects.getString("thursday4", ""));
            items.add(subjects.getString("thursday5", ""));
            items.add(subjects.getString("thursday6", ""));
            items.add(subjects.getString("thursday7", ""));
            items.add(subjects.getString("thursday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            items.add(subjects.getString("friday1", ""));
            items.add(subjects.getString("friday2", ""));
            items.add(subjects.getString("friday3", ""));
            items.add(subjects.getString("friday4", ""));
            items.add(subjects.getString("friday5", ""));
            items.add(subjects.getString("friday6", ""));
            items.add(subjects.getString("friday7", ""));
            items.add(subjects.getString("friday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            items.add("일정이 없습니다");
        }
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.row_widget_item);
        row.setTextViewText(android.R.id.text1, items.get(position));

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(TimeTableWidget.EXTRA_WORD, items.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        items.clear();

        SharedPreferences subjects = mContext.getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            items.add(subjects.getString("monday1", ""));
            items.add(subjects.getString("monday2", ""));
            items.add(subjects.getString("monday3", ""));
            items.add(subjects.getString("monday4", ""));
            items.add(subjects.getString("monday5", ""));
            items.add(subjects.getString("monday6", ""));
            items.add(subjects.getString("monday7", ""));
            items.add(subjects.getString("monday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            items.add(subjects.getString("tuesday1", ""));
            items.add(subjects.getString("tuesday2", ""));
            items.add(subjects.getString("tuesday3", ""));
            items.add(subjects.getString("tuesday4", ""));
            items.add(subjects.getString("tuesday5", ""));
            items.add(subjects.getString("tuesday6", ""));
            items.add(subjects.getString("tuesday7", ""));
            items.add(subjects.getString("tuesday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            items.add(subjects.getString("wednesday1", ""));
            items.add(subjects.getString("wednesday2", ""));
            items.add(subjects.getString("wednesday3", ""));
            items.add(subjects.getString("wednesday4", ""));
            items.add(subjects.getString("wednesday5", ""));
            items.add(subjects.getString("wednesday6", ""));
            items.add(subjects.getString("wednesday7", ""));
            items.add(subjects.getString("wednesday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            items.add(subjects.getString("thursday1", ""));
            items.add(subjects.getString("thursday2", ""));
            items.add(subjects.getString("thursday3", ""));
            items.add(subjects.getString("thursday4", ""));
            items.add(subjects.getString("thursday5", ""));
            items.add(subjects.getString("thursday6", ""));
            items.add(subjects.getString("thursday7", ""));
            items.add(subjects.getString("thursday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            items.add(subjects.getString("friday1", ""));
            items.add(subjects.getString("friday2", ""));
            items.add(subjects.getString("friday3", ""));
            items.add(subjects.getString("friday4", ""));
            items.add(subjects.getString("friday5", ""));
            items.add(subjects.getString("friday6", ""));
            items.add(subjects.getString("friday7", ""));
            items.add(subjects.getString("friday8", ""));
        } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            items.add("일정이 없습니다");
        }
    }
}

