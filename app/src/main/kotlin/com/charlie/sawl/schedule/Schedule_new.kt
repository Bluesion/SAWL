package com.charlie.sawl.schedule

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.charlie.sawl.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.*
import kotlin.collections.ArrayList

class Schedule_new : Fragment(), OnDateSelectedListener {

    private lateinit var mCalendar: MaterialCalendarView
    private lateinit var event: TextView
    private val calendarDays = ArrayList<CalendarDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val day1 = CalendarDay.from(2019, 1, 1)
        calendarDays.add(day1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_schedule, container, false)

        event = rootView.findViewById(R.id.event)
        mCalendar = rootView.findViewById(R.id.calendarView)
        mCalendar.addDecorator(EventDecorator(Color.RED, calendarDays))
        mCalendar.state()
                ?.edit()
                ?.setMinimumDate(CalendarDay.from(2019, Calendar.JANUARY, 1))
                ?.setMaximumDate(CalendarDay.from(2020, Calendar.FEBRUARY, 29))
                ?.commit()
        setCurrentItem()

        return rootView
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        if (date == CalendarDay.from(2019, 3, 1)) {
            event.text = ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.information -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle(R.string.info)
                builder.setMessage(R.string.dialog_text)
                builder.setPositiveButton(android.R.string.ok, null)
                builder.show()
            }
        }
        return true
    }

    private fun setCurrentItem() {
        val calendar = Calendar.getInstance()
        when {
            calendar.get(Calendar.MONTH) == Calendar.JANUARY -> {
                val date = CalendarDay.from(2020, 1, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.FEBRUARY -> {
                val date = CalendarDay.from(2020, 2, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.MARCH -> {
                val date = CalendarDay.from(2019, 3, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.APRIL -> {
                val date = CalendarDay.from(2019, 4, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.MAY -> {
                val date = CalendarDay.from(2019, 5, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.JUNE -> {
                val date = CalendarDay.from(2019, 6, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.JULY -> {
                val date = CalendarDay.from(2019, 7, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.AUGUST -> {
                val date = CalendarDay.from(2019, 8, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER -> {
                val date = CalendarDay.from(2019, 9, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.OCTOBER -> {
                val date = CalendarDay.from(2019, 10, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.NOVEMBER -> {
                val date = CalendarDay.from(2019, 11, 1)
                mCalendar.currentDate = date
            }
            calendar.get(Calendar.MONTH) == Calendar.DECEMBER -> {
                val date = CalendarDay.from(2019, 12, 1)
                mCalendar.currentDate = date
            }
        }
    }
}