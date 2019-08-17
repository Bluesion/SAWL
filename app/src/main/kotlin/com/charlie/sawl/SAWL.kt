package com.charlie.sawl

import java.util.Calendar
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.charlie.sawl.clock.timer.TimerUtil

class SAWL : Application() {

    private var mCurrentAlarmCalendar: Calendar? = null
    var timeString: String? = null

    var currentAlarmCalendar: Calendar
        get() = if (mCurrentAlarmCalendar == null) {
            Calendar.getInstance()
        } else {
            mCurrentAlarmCalendar!!
        }
        set(cal) {
            mCurrentAlarmCalendar = cal
        }

    private val notificationAlarmTitle: String
        get() = getString(R.string.timer)

    override fun onCreate() {
        super.onCreate()
        timeString = ""

        val sharedPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val dark = sharedPrefs.getBoolean("dark", false)
        if (dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun startTimer(milliseconds: Long) {
        mCurrentAlarmCalendar = Calendar.getInstance()
        mCurrentAlarmCalendar!!.add(Calendar.SECOND, (milliseconds / 1000).toInt())
        TimerUtil.setAlarm(applicationContext, 0, milliseconds, notificationAlarmTitle)
    }

    fun stopTimer() {
        TimerUtil.stopTimer(applicationContext)
    }

    fun appendToTimeString(append: String) {
        this.timeString = this.timeString + append
    }

    fun stopNotifyingUser() {
        currentAlarmCalendar.timeInMillis = 0
    }
}
