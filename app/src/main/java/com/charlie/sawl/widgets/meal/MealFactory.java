package com.charlie.sawl.widgets.meal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.charlie.sawl.R;
import com.charlie.sawl.meal.MealTool;
import java.util.ArrayList;

public class MealFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<String> items = new ArrayList<>();
    private Context mContext;

    MealFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        MealTool.todayMealData mBreakfastData = MealTool.todayBreakfast(mContext);
        MealTool.todayMealData mLunchData = MealTool.todayLunch(mContext);
        MealTool.todayMealData mDinnerData = MealTool.todayDinner(mContext);
        String mBreakfast = mBreakfastData.info;
        String mLunch = mLunchData.info;
        String mDinner = mDinnerData.info;

        items.add("아침");
        items.add(mBreakfast);
        items.add("점심");
        items.add(mLunch);
        items.add("저녁");
        items.add(mDinner);
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

        extras.putString(MealWidget.EXTRA_WORD, items.get(position));
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

        MealTool.todayMealData mBreakfastData = MealTool.todayBreakfast(mContext);
        MealTool.todayMealData mLunchData = MealTool.todayLunch(mContext);
        MealTool.todayMealData mDinnerData = MealTool.todayDinner(mContext);
        String mBreakfast = mBreakfastData.info;
        String mLunch = mLunchData.info;
        String mDinner = mDinnerData.info;

        items.add("아침");
        items.add(mBreakfast);
        items.add("점심");
        items.add(mLunch);
        items.add("저녁");
        items.add(mDinner);
    }
}

