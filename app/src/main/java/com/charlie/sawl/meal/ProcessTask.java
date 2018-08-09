package com.charlie.sawl.meal;

import android.content.Context;
import android.os.AsyncTask;

abstract class ProcessTask extends AsyncTask<Integer, Integer, Long> {
    private final Context mContext;

    public abstract void onPreDownload();

    public abstract void onUpdate(int progress);

    public abstract void onFinish(long result);

    ProcessTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        onPreDownload();
    }

    @Override
    protected Long doInBackground(Integer... params) {

        final String year = Integer.toString(params[0]);
        String month = Integer.toString(params[1] + 1);
        String day = Integer.toString(params[2]);

        if (month.length() <= 1)
            month = "0" + month;
        if (day.length() <= 1)
            day = "0" + day;

        try {
            String[] calender = MealLibrary.getDateNew(year, month, day);
            String[] breakfast = MealLibrary.getMealNew("1", year, month, day);
            String[] lunch = MealLibrary.getMealNew("2", year, month, day);
            String[] dinner = MealLibrary.getMealNew("3", year, month, day);

            MealTool.saveMealData(mContext, calender, breakfast, lunch, dinner);

        } catch (Exception e) {
            return -1L;
        }
        return 0L;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {
        onUpdate(params[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        onFinish(result);
    }
}