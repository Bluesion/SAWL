package com.charlie.sawl.meal

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.*
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charlie.sawl.R
import com.charlie.sawl.meal.adapters.BreakfastAdapter
import com.charlie.sawl.meal.adapters.DinnerAdapter
import com.charlie.sawl.meal.adapters.LunchAdapter
import com.charlie.sawl.tools.Preference
import com.charlie.sawl.tools.Tools
import com.google.android.material.picker.CalendarConstraints
import com.google.android.material.picker.MaterialDatePicker
import com.google.android.material.picker.Month
import java.lang.ref.WeakReference
import java.util.*

class Meal : Fragment() {

    private var mBreakfastAdapter: BreakfastAdapter? = null
    private var mLunchAdapter: LunchAdapter? = null
    private var mDinnerAdapter: DinnerAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mCalendar: Calendar? = null
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var dayOfWeek: Int = 0
    private var isUpdating = false
    internal lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private class MyHandler(fragment: Meal): Handler() {
        private val mFragment: WeakReference<Meal> = WeakReference(fragment)

        override fun handleMessage(msg: Message) {
            val fragment = mFragment.get()
            fragment?.handleMessage()
        }
    }

    private fun handleMessage() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_meal, container, false)

        val tabLayout = rootView.findViewById<TabLayout>(R.id.mTabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.breakfast))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.lunch))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dinner))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.addOnTabSelectedListener(onTabSelectedListener())

        val horizontalLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mRecyclerView = rootView.findViewById(R.id.mRecyclerView)
        mBreakfastAdapter = BreakfastAdapter(activity)
        mLunchAdapter = LunchAdapter(activity)
        mDinnerAdapter = DinnerAdapter(activity)
        mRecyclerView!!.adapter = mBreakfastAdapter
        mRecyclerView!!.layoutManager = horizontalLayoutManager

        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener {
            getCalendarInstance(true)
            getMealList(true)
            if (mSwipeRefreshLayout.isRefreshing)
                mSwipeRefreshLayout.isRefreshing = false
        }
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_red, R.color.refresh_green,
                R.color.refresh_purple, R.color.refresh_blue)

        getMealList(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_meal, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                if (Tools.isOnline(activity!!.applicationContext)) {
                    getCalendarInstance(false)
                    if (mCalendar == null)
                        mCalendar = Calendar.getInstance()

                    val dayOfWeek = mCalendar!!.get(Calendar.DAY_OF_WEEK)
                    mCalendar!!.add(Calendar.DATE, 2 - dayOfWeek)

                    val year = mCalendar!!.get(Calendar.YEAR)
                    val month = mCalendar!!.get(Calendar.MONTH)
                    val day = mCalendar!!.get(Calendar.DAY_OF_MONTH)

                    val mPrefBreakfastName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_BREAKFAST)
                    val mPrefLunchName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_LUNCH)
                    val mPrefDinnerName = MealTool.getMealStringFormat(year, month + 1, day, MealTool.TYPE_DINNER)

                    val mPref = Preference(activity!!.applicationContext, MealTool.MEAL_PREFERENCE_NAME)
                    mPref.remove(mPrefBreakfastName)
                    mPref.remove(mPrefLunchName)
                    mPref.remove(mPrefDinnerName)

                    getMealList(true)
                } else {
                    val builder = AlertDialog.Builder(activity!!)
                    builder.setTitle(R.string.refresh_title)
                    builder.setMessage(R.string.refresh_text)
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.show()
                }
            }
            R.id.date -> {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                datePicker.setCalendarConstraints(CalendarConstraints.Builder().apply {
                    setStart(Month.create(2019, Calendar.AUGUST))
                    setEnd(Month.create(2036, Calendar.AUGUST))
                }.build())
                datePicker.build().apply {
                    addOnPositiveButtonClickListener { time ->
                        mCalendar = Calendar.getInstance()
                        val temp = Tools.longToString(time)
                        val y = temp.substring(0, 4).toInt()
                        val m = temp.substring(5, 7).toInt() - 1
                        val d = temp.substring(8, 10).toInt()
                        mCalendar!!.set(y, m, d)
                        year = y
                        month = m
                        day = d
                        dayOfWeek = mCalendar!!.get(Calendar.DAY_OF_WEEK)
                        getMealList(true)
                    }
                }.show(fragmentManager!!, "Illusion")
            }
            R.id.allergy -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle(R.string.allergy_title)
                builder.setMessage(R.string.allergy_text)
                builder.setPositiveButton(android.R.string.ok, null)
                builder.show()
            }
        }
        return true
    }

    private fun onTabSelectedListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> mRecyclerView!!.adapter = mBreakfastAdapter
                    1 -> mRecyclerView!!.adapter = mLunchAdapter
                    else -> mRecyclerView!!.adapter = mDinnerAdapter
                }
                setCurrentItem()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
    }

    private fun getCalendarInstance(getInstance: Boolean) {
        if (getInstance || mCalendar == null)
            mCalendar = Calendar.getInstance()
        year = mCalendar!!.get(Calendar.YEAR)
        month = mCalendar!!.get(Calendar.MONTH)
        day = mCalendar!!.get(Calendar.DAY_OF_MONTH)
        dayOfWeek = mCalendar!!.get(Calendar.DAY_OF_WEEK)
    }

    private fun getMealList(isUpdate: Boolean) {
        mBreakfastAdapter!!.clearData()
        mLunchAdapter!!.clearData()
        mDinnerAdapter!!.clearData()
        getCalendarInstance(false)

        val mToday = Calendar.getInstance()
        val todayYear = mToday.get(Calendar.YEAR)
        val todayMonth = mToday.get(Calendar.MONTH)
        val todayDay = mToday.get(Calendar.DAY_OF_MONTH)

        if (mCalendar == null)
            mCalendar = Calendar.getInstance()

        dayOfWeek = mCalendar!!.get(Calendar.DAY_OF_WEEK)
        mCalendar!!.add(Calendar.DATE, 2 - dayOfWeek)

        for (i in 0..6) {
            val year = mCalendar!!.get(Calendar.YEAR)
            val month = mCalendar!!.get(Calendar.MONTH)
            val day = mCalendar!!.get(Calendar.DAY_OF_MONTH)

            val mData = MealTool.restoreMealData(activity!!, year, month, day)

            if (mData.isBlankDay) {
                if (isUpdate) {
                    if (!isUpdating) {
                        mSwipeRefreshLayout.isRefreshing = true
                        networkTask(year, month, day)
                        isUpdating = true
                    }
                }
                return
            }

            if (year == todayYear && month == todayMonth && day == todayDay) {
                mBreakfastAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.breakfast!!, true)
                mLunchAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.lunch!!, true)
                mDinnerAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.dinner!!, true)
            } else {
                mBreakfastAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.breakfast!!, false)
                mLunchAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.lunch!!, false)
                mDinnerAdapter!!.addItem(mData.calendar!!, mData.dayOfTheWeek!!, mData.dinner!!, false)
            }
            mCalendar!!.add(Calendar.DATE, 1)
        }

        mCalendar!!.set(year, month, day)
        mBreakfastAdapter!!.notifyDataSetChanged()
        mLunchAdapter!!.notifyDataSetChanged()
        mDinnerAdapter!!.notifyDataSetChanged()
        setCurrentItem()
    }

    private fun setCurrentItem() {
        when {
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY -> mRecyclerView!!.smoothScrollToPosition(0)
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY -> mRecyclerView!!.smoothScrollToPosition(1)
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY -> mRecyclerView!!.smoothScrollToPosition(2)
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY -> mRecyclerView!!.smoothScrollToPosition(3)
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY -> mRecyclerView!!.smoothScrollToPosition(4)
            mCalendar!!.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY -> mRecyclerView!!.smoothScrollToPosition(5)
            else -> mRecyclerView!!.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        super.onPause()

        mSwipeRefreshLayout.isRefreshing = false
        mCalendar = null
    }

    private fun networkTask(a: Int, b: Int, c: Int) {
        val mHandler = MyHandler(this@Meal)
        object : Thread() {
            override fun run() {
                mHandler.post { mSwipeRefreshLayout.isRefreshing = true }
                val year = a.toString()
                var month = b.plus(1).toString()
                var day = c.toString()

                if (month.length <= 1)
                    month = "0$month"
                if (day.length <= 1)
                    day = "0$day"

                try {
                    val calender = MealLibrary.getDateNew(year, month, day)
                    val breakfast = MealLibrary.getMealNew("1", year, month, day)
                    val lunch = MealLibrary.getMealNew("2", year, month, day)
                    val dinner = MealLibrary.getMealNew("3", year, month, day)

                    MealTool.saveMealData(activity!!, calender, breakfast, lunch, dinner)

                } catch (e: Exception) {}

                mHandler.post {
                    mSwipeRefreshLayout.isRefreshing = false
                    isUpdating = false
                    getMealList(false)
                }
            }
        }.start()
    }
}