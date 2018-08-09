package com.charlie.sawl.widgets.meal;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MealWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new MealFactory(this.getApplicationContext(), intent));
    }
}