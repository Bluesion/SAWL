package com.charlie.sawl.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.charlie.sawl.R
import com.charlie.sawl.meal.MealTool
import com.charlie.sawl.timetable.TimetableDBHelper
import com.charlie.sawl.tools.Tools
import com.google.android.material.tabs.TabLayout
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

class Main : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var mealText: TextView
    private lateinit var mDBHelper: TimetableDBHelper
    private lateinit var temperature: TextView
    private lateinit var temperatureText: TextView
    private lateinit var dust: TextView
    private lateinit var dustText: TextView
    private var temperatureString = ""
    private var dustString = ""
    private lateinit var weatherInternet: LinearLayout
    private lateinit var weatherNoInternet: TextView
    private val handler = MyHandler(this@Main)

    private class MyHandler (fragment: Main) : Handler() {
        private val mFragment: WeakReference<Main> = WeakReference(fragment)

        override fun handleMessage(msg: Message) {
            val fragment = mFragment.get()
            fragment?.handleMessage()
        }
    }

    private fun handleMessage() {}

    private val dDayString: String
        get() {
            val mCalendar = Calendar.getInstance()
            mCalendar.set(2019, Calendar.NOVEMBER, 14, 0, 0, 0)

            val diffDays = ((mCalendar.timeInMillis - System.currentTimeMillis()) / 86400000).toInt()
            return if (diffDays > 0) {
                "-$diffDays"
            } else {
                "+" + -diffDays
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        val counter = rootView.findViewById<TextView>(R.id.countdown)
        counter!!.text = dDayString

        tabLayout = rootView.findViewById(R.id.mTabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("아침"))
        tabLayout.addTab(tabLayout.newTab().setText("점심"))
        tabLayout.addTab(tabLayout.newTab().setText("저녁"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.addOnTabSelectedListener(onTabSelectedListener())
        mealText = rootView.findViewById(R.id.meal)
        mealText.text = MealTool.todayBreakfast(activity!!).info

        temperatureText = rootView.findViewById(R.id.temperatureText)
        temperature = rootView.findViewById(R.id.temperature)
        dustText = rootView.findViewById(R.id.dustText)
        dust = rootView.findViewById(R.id.dust)
        weatherInternet = rootView.findViewById(R.id.weatherInternet)
        weatherNoInternet = rootView.findViewById(R.id.weatherNoInternet)

        val sharedPrefs = activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val save = sharedPrefs.getBoolean("save", true)

        if (save) {
            if (Tools.isWifi(activity!!)) {
                networkTask()
            } else {
                weatherInternet.visibility = View.GONE
                weatherNoInternet.visibility = View.VISIBLE
            }
        } else {
            if (Tools.isOnline(activity!!)) {
                networkTask()
            } else {
                weatherInternet.visibility = View.GONE
                weatherNoInternet.visibility = View.VISIBLE
            }
        }

        val day = rootView.findViewById<TextView>(R.id.day)
        val table1 = rootView.findViewById<TextView>(R.id.timetable1)
        val table2 = rootView.findViewById<TextView>(R.id.timetable2)
        val table3 = rootView.findViewById<TextView>(R.id.timetable3)
        val table4 = rootView.findViewById<TextView>(R.id.timetable4)
        val table5 = rootView.findViewById<TextView>(R.id.timetable5)
        val table6 = rootView.findViewById<TextView>(R.id.timetable6)
        val table7 = rootView.findViewById<TextView>(R.id.timetable7)
        val table8 = rootView.findViewById<TextView>(R.id.timetable8)

        val mCalendar = Calendar.getInstance()
        mDBHelper = TimetableDBHelper(activity!!)
        val subjects = activity!!.getSharedPreferences("timetable", Activity.MODE_PRIVATE)

        val free = rootView.findViewById<LinearLayout>(R.id.no_timetable)
        val study = rootView.findViewById<LinearLayout>(R.id.timetable_layout)

        if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            free.visibility = View.GONE

            day.text = getString(R.string.monday)

            val mon1 = subjects.getString("monday1", "")
            val mon2 = subjects.getString("monday2", "")
            val mon3 = subjects.getString("monday3", "")
            val mon4 = subjects.getString("monday4", "")
            val mon5 = subjects.getString("monday5", "")
            val mon6 = subjects.getString("monday6", "")
            val mon7 = subjects.getString("monday7", "")
            val mon8 = subjects.getString("monday8", "")

            table1.text = mon1
            table2.text = mon2
            table3.text = mon3
            table4.text = mon4
            table5.text = mon5
            table6.text = mon6
            table7.text = mon7
            table8.text = mon8
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            free.visibility = View.GONE

            day.text = getString(R.string.tuesday)

            val tue1 = subjects.getString("tuesday1", "")
            val tue2 = subjects.getString("tuesday2", "")
            val tue3 = subjects.getString("tuesday3", "")
            val tue4 = subjects.getString("tuesday4", "")
            val tue5 = subjects.getString("tuesday5", "")
            val tue6 = subjects.getString("tuesday6", "")
            val tue7 = subjects.getString("tuesday7", "")
            val tue8 = subjects.getString("tuesday8", "")

            table1.text = tue1
            table2.text = tue2
            table3.text = tue3
            table4.text = tue4
            table5.text = tue5
            table6.text = tue6
            table7.text = tue7
            table8.text = tue8
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            free.visibility = View.GONE

            day.text = getString(R.string.wednesday)

            val wed1 = subjects.getString("wednesday1", "")
            val wed2 = subjects.getString("wednesday2", "")
            val wed3 = subjects.getString("wednesday3", "")
            val wed4 = subjects.getString("wednesday4", "")
            val wed5 = subjects.getString("wednesday5", "")
            val wed6 = subjects.getString("wednesday6", "")
            val wed7 = subjects.getString("wednesday7", "")
            val wed8 = subjects.getString("wednesday8", "")

            table1.text = wed1
            table2.text = wed2
            table3.text = wed3
            table4.text = wed4
            table5.text = wed5
            table6.text = wed6
            table7.text = wed7
            table8.text = wed8
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            free.visibility = View.GONE

            day.text = getString(R.string.thursday)

            val thu1 = subjects.getString("thursday1", "")
            val thu2 = subjects.getString("thursday2", "")
            val thu3 = subjects.getString("thursday3", "")
            val thu4 = subjects.getString("thursday4", "")
            val thu5 = subjects.getString("thursday5", "")
            val thu6 = subjects.getString("thursday6", "")
            val thu7 = subjects.getString("thursday7", "")
            val thu8 = subjects.getString("thursday8", "")

            table1.text = thu1
            table2.text = thu2
            table3.text = thu3
            table4.text = thu4
            table5.text = thu5
            table6.text = thu6
            table7.text = thu7
            table8.text = thu8
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            free.visibility = View.GONE

            day.text = getString(R.string.friday)

            val fri1 = subjects.getString("friday1", "")
            val fri2 = subjects.getString("friday2", "")
            val fri3 = subjects.getString("friday3", "")
            val fri4 = subjects.getString("friday4", "")
            val fri5 = subjects.getString("friday5", "")
            val fri6 = subjects.getString("friday6", "")
            val fri7 = subjects.getString("friday7", "")
            val fri8 = subjects.getString("friday8", "")

            table1.text = fri1
            table2.text = fri2
            table3.text = fri3
            table4.text = fri4
            table5.text = fri5
            table6.text = fri6
            table7.text = fri7
            table8.text = fri8
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            study.visibility = View.GONE
        }

        return rootView
    }

    private fun onTabSelectedListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when {
                    tab.position == 0 -> mealText.text = MealTool.todayBreakfast(activity!!).info
                    tab.position == 1 -> mealText.text = MealTool.todayLunch(activity!!).info
                    else -> mealText.text = MealTool.todayDinner(activity!!).info
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                mealText.text = ""
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
    }

    private fun networkTask() {
        val mHandler = MyHandler(this@Main)
        object : Thread() {
            override fun run() {
                mHandler.post { }
                try {
                    val doc = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EA%B2%BD%EA%B8%B0%EB%8F%84+%EC%88%98%EC%9B%90%EC%8B%9C+%EC%98%81%ED%86%B5%EA%B5%AC+%EC%9D%B4%EC%9D%98%EB%8F%99+%EB%82%A0%EC%94%A8").get()
                    val sign = "span.blind"
                    val detail = "span.num"
                    doc.select(sign).remove()
                    doc.select(detail).remove()
                    val temperatureText = doc.select("#main_pack div.sc.cs_weather._weather div div.weather_box div.weather_area._mainArea div.today_area._mainTabContent div.main_info div.info_data p.info_temperature")
                    val dustText = doc.select("#main_pack div.sc.cs_weather._weather div div.weather_box div.weather_area._mainArea div.today_area._mainTabContent div.sub_info div.detail_box dl.indicator dd.lv2")

                    for (el in temperatureText) {
                        temperatureString = el.text()
                    }

                    for (el in dustText) {
                        dustString = el.text()
                    }

                } catch (ignored: IOException) {}

                mHandler.post {
                    if (temperatureString.isEmpty() or (temperatureString == "")) {
                        weatherInternet.visibility = View.GONE
                        weatherNoInternet.visibility = View.VISIBLE
                    } else {
                        weatherInternet.visibility = View.VISIBLE
                        weatherNoInternet.visibility = View.GONE
                        temperature.text = temperatureString
                    }

                    if (dustString.isEmpty() or (dustString == "")) {
                        weatherInternet.visibility = View.GONE
                        weatherNoInternet.visibility = View.VISIBLE
                    } else {
                        weatherInternet.visibility = View.VISIBLE
                        weatherNoInternet.visibility = View.GONE
                        dust.text = dustString
                    }
                    handler.sendEmptyMessage(0)
                }

            }
        }.start()
    }
}