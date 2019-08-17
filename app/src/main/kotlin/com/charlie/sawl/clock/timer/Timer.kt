package com.charlie.sawl.clock.timer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.charlie.sawl.R
import com.charlie.sawl.SAWL
import com.google.android.material.button.MaterialButton
import java.util.*

class Timer : Fragment() {

    private lateinit var mTimer1: MaterialButton
    private lateinit var mTimer2: MaterialButton
    private lateinit var mTimer3: MaterialButton
    private lateinit var mTimer4: MaterialButton
    private lateinit var mTimer5: MaterialButton
    private lateinit var mTimer6: MaterialButton
    private lateinit var mTimer7: MaterialButton
    private lateinit var mTimer8: MaterialButton
    private lateinit var mTimer9: MaterialButton
    private lateinit var mTimer0: MaterialButton
    private lateinit var mStartButton: MaterialButton
    private lateinit var mPauseButton: MaterialButton
    private lateinit var mContinueButton: MaterialButton
    private lateinit var mResetButton: MaterialButton
    private var mAlarmApplication: SAWL? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mCountingDown: Boolean = false
    private lateinit var timerValue: TextView
    private lateinit var sharedPrefs: SharedPreferences
    private var mTemp: Long = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_timer, container, false)

        mAlarmApplication = activity!!.applicationContext as SAWL

        timerValue = rootView.findViewById(R.id.timerValue)
        mTimer0 = rootView.findViewById(R.id.timer_0)
        mTimer1 = rootView.findViewById(R.id.timer_1)
        mTimer2 = rootView.findViewById(R.id.timer_2)
        mTimer3 = rootView.findViewById(R.id.timer_3)
        mTimer4 = rootView.findViewById(R.id.timer_4)
        mTimer5 = rootView.findViewById(R.id.timer_5)
        mTimer6 = rootView.findViewById(R.id.timer_6)
        mTimer7 = rootView.findViewById(R.id.timer_7)
        mTimer8 = rootView.findViewById(R.id.timer_8)
        mTimer9 = rootView.findViewById(R.id.timer_9)
        mTimer0.setOnClickListener(timerClickListener())
        mTimer1.setOnClickListener(timerClickListener())
        mTimer2.setOnClickListener(timerClickListener())
        mTimer3.setOnClickListener(timerClickListener())
        mTimer4.setOnClickListener(timerClickListener())
        mTimer5.setOnClickListener(timerClickListener())
        mTimer6.setOnClickListener(timerClickListener())
        mTimer7.setOnClickListener(timerClickListener())
        mTimer8.setOnClickListener(timerClickListener())
        mTimer9.setOnClickListener(timerClickListener())

        mStartButton = rootView.findViewById(R.id.startButton)
        mPauseButton = rootView.findViewById(R.id.pauseButton)
        mContinueButton = rootView.findViewById(R.id.continueButton)
        mResetButton = rootView.findViewById(R.id.resetButton)
        mStartButton.setOnClickListener {
            mStartButton.visibility = View.GONE
            mPauseButton.visibility = View.VISIBLE
            mContinueButton.visibility = View.GONE
            mResetButton.visibility = View.GONE
            mAlarmApplication!!.startTimer(TimeConversionUtil.convertStringToMilliseconds(mAlarmApplication!!.timeString!!))
            startTextCountdown()
            mCountingDown = true
            updateButtons()
        }
        mPauseButton.setOnClickListener {
            mStartButton.visibility = View.GONE
            mPauseButton.visibility = View.GONE
            mContinueButton.visibility = View.VISIBLE
            mResetButton.visibility = View.VISIBLE
            mAlarmApplication!!.stopTimer()
            stopTextCountdown()
            mCountingDown = false
            updateButtons()
        }
        mContinueButton.setOnClickListener {
            mStartButton.visibility = View.GONE
            mPauseButton.visibility = View.VISIBLE
            mContinueButton.visibility = View.GONE
            mResetButton.visibility = View.GONE
            mAlarmApplication!!.startTimer(mTemp)
            startTextCountdown()
            mCountingDown = true
            updateButtons()
        }
        mResetButton.setOnClickListener {
            mStartButton.visibility = View.VISIBLE
            mPauseButton.visibility = View.GONE
            mContinueButton.visibility = View.GONE
            mResetButton.visibility = View.GONE
            mAlarmApplication!!.timeString = ""
            timerValue.setText(R.string.default_time_timer)
            mAlarmApplication!!.stopTimer()
            stopTextCountdown()
            mCountingDown = false
            updateButtons()
        }

        sharedPrefs = activity!!.getSharedPreferences("timer", Context.MODE_PRIVATE)
        mCountingDown = false
        restoreText()

        updateTimeView()
        updateButtons()
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }
        startTextCountdown()
        if (!mCountingDown) {
            mAlarmApplication!!.stopTimer()
        }

        return rootView
    }

    private fun restoreText() {
        val milliseconds = sharedPrefs.getLong(ALARM_TIME, 0)
        val c = Calendar.getInstance()
        c.timeInMillis = milliseconds
        mAlarmApplication!!.currentAlarmCalendar = c
    }

    private fun timerClickListener(): View.OnClickListener {
        return View.OnClickListener { v ->
            if (!mCountingDown) {
                if (mAlarmApplication!!.timeString!!.length < TIME_MAX_LENGTH) {
                    mAlarmApplication!!.appendToTimeString((v as Button).text.toString())
                    updateTimeView()
                } else {
                    Toast.makeText(activity!!.applicationContext, R.string.time_too_long_warning, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateTimeView() {
        val hours = TimeConversionUtil.getHoursFromTimeString(mAlarmApplication!!.timeString!!)
        val minutes = TimeConversionUtil.getMinutesFromTimeString(mAlarmApplication!!.timeString!!)
        val seconds = TimeConversionUtil.getSecondsFromTimeString(mAlarmApplication!!.timeString!!)
        timerValue.text = (String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds))
    }

    override fun onStop() {
        super.onStop()
        val editor = sharedPrefs.edit()
        editor.putLong(ALARM_TIME, mAlarmApplication!!.currentAlarmCalendar.timeInMillis)
        editor.apply()
    }

    private fun startTextCountdown() {
        val c = mAlarmApplication!!.currentAlarmCalendar
        if (c.timeInMillis != 0L) {
            val alarmTime = c.timeInMillis
            val currentTime = Calendar.getInstance().timeInMillis
            val timeDifference = alarmTime - currentTime
            mCountingDown = timeDifference > 0
            mCountDownTimer = object : CountDownTimer(timeDifference, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerValue.text = TimeConversionUtil.getTimeStringFromMilliseconds(millisUntilFinished)
                    mTemp = millisUntilFinished
                }

                override fun onFinish() {
                    timerValue.setText(R.string.default_time_timer)
                    mCountingDown = false
                }
            }
            mCountDownTimer!!.start()
        } else {
            mCountingDown = false
        }
        updateButtons()
    }

    private fun stopTextCountdown() {
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }
    }

    private fun updateButtons() {
        mStartButton.isEnabled = !mCountingDown
        mTimer0.isEnabled = !mCountingDown
        mTimer1.isEnabled = !mCountingDown
        mTimer2.isEnabled = !mCountingDown
        mTimer3.isEnabled = !mCountingDown
        mTimer4.isEnabled = !mCountingDown
        mTimer5.isEnabled = !mCountingDown
        mTimer6.isEnabled = !mCountingDown
        mTimer7.isEnabled = !mCountingDown
        mTimer8.isEnabled = !mCountingDown
        mTimer9.isEnabled = !mCountingDown
    }

    companion object {
        private const val ALARM_TIME = "alarmkey"
        private const val TIME_MAX_LENGTH = 6
    }
}