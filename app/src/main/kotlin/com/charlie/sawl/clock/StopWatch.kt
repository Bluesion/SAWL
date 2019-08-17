package com.charlie.sawl.clock

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charlie.sawl.R
import android.widget.TextView
import android.os.SystemClock
import com.google.android.material.button.MaterialButton

class StopWatch : Fragment() {

    private lateinit var timerValue: TextView
    private var startTime = 0L
    private val customHandler = Handler()

    var timeInMilliseconds = 0L
    var timeSwapBuff = 0L
    var updatedTime = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false)

        timerValue = rootView.findViewById(R.id.timeValue)
        val startButton = rootView.findViewById<MaterialButton>(R.id.startButton)
        val pauseButton = rootView.findViewById<MaterialButton>(R.id.pauseButton)
        val continueButton = rootView.findViewById<MaterialButton>(R.id.continueButton)
        val resetButton = rootView.findViewById<MaterialButton>(R.id.resetButton)

        startButton.setOnClickListener {
            startTime = SystemClock.uptimeMillis()
            customHandler.postDelayed(updateTimerThread, 0)
            startButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
            continueButton.visibility = View.GONE
            resetButton.visibility = View.GONE
        }

        pauseButton.setOnClickListener {
            timeSwapBuff += timeInMilliseconds
            customHandler.removeCallbacks(updateTimerThread)
            startButton.visibility = View.GONE
            pauseButton.visibility = View.GONE
            continueButton.visibility = View.VISIBLE
            resetButton.visibility = View.VISIBLE
        }

        continueButton.setOnClickListener {
            startTime = SystemClock.uptimeMillis()
            customHandler.postDelayed(updateTimerThread, 0)
            startButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
            continueButton.visibility = View.GONE
            resetButton.visibility = View.GONE
        }

        resetButton.setOnClickListener {
            timerValue.text = getText(R.string.default_time_stopwatch)
            timeInMilliseconds = 0
            startTime = 0
            timeSwapBuff = 0
            updatedTime = 0
            customHandler.removeCallbacksAndMessages(updateTimerThread)
            startButton.visibility = View.VISIBLE
            pauseButton.visibility = View.GONE
            continueButton.visibility = View.GONE
            resetButton.visibility = View.GONE
        }

        return rootView
    }

    private val updateTimerThread = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            updatedTime = timeSwapBuff + timeInMilliseconds

            var seconds = (updatedTime / 1000).toInt()
            val minutes = seconds / 60
            seconds %= 60

            timerValue.text = String.format("%d:%02d", minutes, seconds);
            customHandler.postDelayed(this, 0)
        }
    }
}