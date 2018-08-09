package com.charlie.sawl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.charlie.sawl.memo.AlarmReceiver;
import com.charlie.sawl.memo.MemoClass;
import com.charlie.sawl.memo.MemoDatabase;
import com.charlie.sawl.widgets.CounterWidget;
import com.charlie.sawl.widgets.meal.MealWidget;
import com.charlie.sawl.widgets.timetable.TimeTableWidget;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {

    private long mRepeatTime;
    private static final long milMinute = 60000L, milHour = 3600000L, milDay = 86400000L, milWeek = 604800000L, milMonth = 2592000000L;

    @Override
    public void onReceive(Context context, Intent intent) {
        MealWidget.updateWidget(context);
        TimeTableWidget.updateWidget(context);
        CounterWidget.updateCounter(context);

        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            Calendar mCalendar = Calendar.getInstance();
            AlarmManager mAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent mIntentDate = new Intent(context, BootReceiver.class);
            PendingIntent mPending = PendingIntent.getBroadcast(context, 0, mIntentDate, 0);
            mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH) + 1, 1, 0);
            mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 6 * 60 * 60 * 1000, mPending);

            MemoDatabase rb = new MemoDatabase(context);
            AlarmReceiver mAlarmReceiver = new AlarmReceiver();

            List<MemoClass> memoClasses = rb.getAllMemo();

            for (MemoClass rm : memoClasses) {
                int mReceivedID = rm.getID();
                String mRepeat = rm.getRepeat();
                String mRepeatNo = rm.getRepeatNo();
                String mRepeatType = rm.getRepeatType();
                String mActive = rm.getActive();
                String mDate = rm.getDate();
                String mTime = rm.getTime();

                String[] mDateSplit = mDate.split("/");
                String[] mTimeSplit = mTime.split(":");

                int mDay = Integer.parseInt(mDateSplit[0]);
                int mMonth = Integer.parseInt(mDateSplit[1]);
                int mYear = Integer.parseInt(mDateSplit[2]);
                int mHour = Integer.parseInt(mTimeSplit[0]);
                int mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                switch (mRepeatType) {
                    case "분":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                        break;
                    case "시간":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                        break;
                    case "일":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                        break;
                    case "주":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                        break;
                    case "개월":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                        break;
                }

                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }
        }
    }
}