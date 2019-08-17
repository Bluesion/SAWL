package com.charlie.sawl.widgets.timetable

import android.content.Intent
import android.widget.RemoteViewsService

class TimetableWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return TimetableFactory(this.applicationContext, intent)
    }
}