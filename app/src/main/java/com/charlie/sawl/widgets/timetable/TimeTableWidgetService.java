package com.charlie.sawl.widgets.timetable;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TimeTableWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new TimeTableFactory(this.getApplicationContext(), intent));
    }
}