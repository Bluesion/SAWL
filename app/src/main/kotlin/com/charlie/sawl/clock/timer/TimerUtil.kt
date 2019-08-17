package com.charlie.sawl.clock.timer

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.charlie.sawl.MainActivity
import com.charlie.sawl.R
import com.charlie.sawl.SAWL

object TimerUtil {
    const val MILLISECONDS_LEFT_KEY = "ms_left"
    const val TITLE_KEY = "timer_title"
    const val CANCEL_ALARM_KEY = "cancel_alarm"
    private const val NOTIFICATION_ID = 4439
    private const val PENDING_INTENT_ID = 94549

    fun stopTimer(applicationContext: Context) {
        getAlarmManager(applicationContext).cancel(PendingIntent.getBroadcast(applicationContext,
                        PENDING_INTENT_ID, Intent(applicationContext, TimerReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT))
        getNotificationManager(applicationContext).cancel(NOTIFICATION_ID)
        getApplication(applicationContext).stopNotifyingUser()
    }

    fun setAlarm(applicationContext: Context, millisecondsUntilAlarm: Long, totalMillisecondsLeft: Long, title: String) {
        val alarmTime = Calendar.getInstance()
        alarmTime.add(Calendar.SECOND, (millisecondsUntilAlarm / 1000).toInt())
        getAlarmManager(applicationContext).set(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis,
                getTimerPendingIntent(applicationContext, totalMillisecondsLeft, title, false))
    }

    fun getNotificationTitleText(applicationContext: Context, timerName: String, milliseconds: Long): String {
        val time = Calendar.getInstance()
        time.add(Calendar.SECOND, (milliseconds / 1000).toInt())
        val sdf = SimpleDateFormat("h:mm:ss aa", Locale.getDefault())
        return timerName + " - " + sdf.format(time.time)
    }

    private fun getTimeRemainingText(applicationContext: Context, millisecondsLeft: Long): String {
        val hours = TimeConversionUtil.getHoursFromMilliseconds(millisecondsLeft)
        val minutes = TimeConversionUtil.getMinutesFromMilliseconds(millisecondsLeft)
        val seconds = TimeConversionUtil.getSecondsFromMilliseconds(millisecondsLeft)
        return if (hours > 0 || minutes > 0) {
            getHoursMinutesText(applicationContext, hours, minutes)
        } else {
            getSecondsText(applicationContext, seconds)
        }
    }

    private fun getHoursMinutesText(applicationContext: Context,
                                    hours: Int, minutes: Int): String {
        val res = applicationContext.resources
        var result = ""
        if (hours > 1) {
            result += (hours.toString() + " " + res.getString(R.string.hours) + " ")
        } else if (hours == 1) {
            result += (hours.toString() + " " + res.getString(R.string.hour) + " ")
        }
        if (minutes > 1) {
            result += (minutes.toString() + " " + res.getString(R.string.minutes) + " ")
        } else if (minutes == 1) {
            result += (minutes.toString() + " " + res.getString(R.string.minute) + " ")
        }
        result += res.getString(R.string.remaining)
        return result
    }

    private fun getSecondsText(applicationContext: Context, seconds: Int): String {
        val res = applicationContext.resources
        var result = ""
        if (seconds > 1) {
            result += (seconds.toString() + " " + res.getString(R.string.seconds) + " ")
        } else if (seconds == 1) {
            result += (seconds.toString() + " " + res.getString(R.string.second) + " ")
        }
        result += res.getString(R.string.remaining)
        return result
    }

    fun updateNotificationContentText(applicationContext: Context, title: String, milliseconds: Long) {
        getNotificationManager(applicationContext).notify(NOTIFICATION_ID,
                getNotificationBuilder(applicationContext, title, getTimeRemainingText(applicationContext, milliseconds)).build())
    }

    fun alertTimerHasFinished(applicationContext: Context, title: String) {
        val builder = getNotificationBuilder(applicationContext, title,
                applicationContext.resources.getString(R.string.notification_done_text))

        builder.setOngoing(false).setDeleteIntent(getTimerPendingIntent(applicationContext, 0, title, true))
        getNotificationManager(applicationContext).notify(NOTIFICATION_ID, builder.build())
    }

    private fun getNotificationBuilder(applicationContext: Context, title: String, content: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext)
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val contentIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val icon = R.drawable.nav_clock
        val `when` = System.currentTimeMillis()

        builder.setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(`when`)
                .setOngoing(true)
                .setContentIntent(contentIntent)

        return builder
    }

    private fun getTimerPendingIntent(applicationContext: Context, millisecondsLeft: Long, title: String, cancelAlarm: Boolean): PendingIntent {
        val intent = Intent(applicationContext, TimerReceiver::class.java)
        intent.putExtra(MILLISECONDS_LEFT_KEY, millisecondsLeft)
        intent.putExtra(TITLE_KEY, title)
        intent.putExtra(CANCEL_ALARM_KEY, cancelAlarm)
        return PendingIntent.getBroadcast(applicationContext, PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getApplication(applicationContext: Context): SAWL {
        return applicationContext as SAWL
    }

    private fun getAlarmManager(applicationContext: Context): AlarmManager {
        return applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getNotificationManager(applicationContext: Context): NotificationManager {
        return applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}
