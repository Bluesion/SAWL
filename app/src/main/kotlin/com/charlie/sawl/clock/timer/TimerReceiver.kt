package com.charlie.sawl.clock.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(TimerUtil.TITLE_KEY)
        var millisecondsLeft = intent.getLongExtra(TimerUtil.MILLISECONDS_LEFT_KEY, 0)
        when {
            intent.getBooleanExtra(TimerUtil.CANCEL_ALARM_KEY, false) -> TimerUtil.stopTimer(context.applicationContext)
            millisecondsLeft <= SECOND -> TimerUtil.alertTimerHasFinished(context.applicationContext, title)
            else -> {
                val hours = TimeConversionUtil.getHoursFromMilliseconds(millisecondsLeft)
                val minutes = TimeConversionUtil.getMinutesFromMilliseconds(millisecondsLeft)
                val seconds = TimeConversionUtil.getSecondsFromMilliseconds(millisecondsLeft)
                if (hours > 0 || minutes > 0) {
                    if (hours == 0 && minutes == 1 && seconds == 0) {
                        millisecondsLeft -= SECOND
                        TimerUtil.setAlarm(context.applicationContext, SECOND, millisecondsLeft, title)
                    } else if (seconds > 0) {
                        millisecondsLeft -= seconds * SECOND
                        TimerUtil.setAlarm(context.applicationContext, seconds * SECOND, millisecondsLeft, title)
                    } else {
                        millisecondsLeft -= MINUTE
                        TimerUtil.setAlarm(context.applicationContext, MINUTE, millisecondsLeft, title)
                    }
                } else {
                    millisecondsLeft -= SECOND
                    TimerUtil.setAlarm(context.applicationContext, SECOND, millisecondsLeft, title)
                }
                TimerUtil.updateNotificationContentText(context.applicationContext, title, millisecondsLeft)
            }
        }
    }

    companion object {
        const val SECOND: Long = 1000
        const val MINUTE = SECOND * 60
    }
}
