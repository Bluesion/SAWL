package com.charlie.sawl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.charlie.sawl.widgets.CounterWidget
import com.charlie.sawl.widgets.meal.MealWidget
import com.charlie.sawl.widgets.timetable.TimetableWidget

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            MealWidget.updateWidget(context)
            TimetableWidget.updateWidget(context)
            CounterWidget.updateCounter(context)
        }
    }
}