package com.charlie.sawl.widgets.meal

import android.content.Intent
import android.widget.RemoteViewsService

class MealWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return MealFactory(this.applicationContext)
    }
}