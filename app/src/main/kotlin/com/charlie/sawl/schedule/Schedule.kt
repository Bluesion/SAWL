package com.charlie.sawl.schedule

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.charlie.sawl.R
import java.util.ArrayList
import java.util.Calendar

class Schedule : Fragment() {

    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab, container, false)

        mViewPager = rootView.findViewById(R.id.mViewPager)
        setupViewPager(mViewPager)

        val tabLayout = rootView.findViewById<TabLayout>(R.id.mTabLayout)
        tabLayout.setupWithViewPager(mViewPager)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        setCurrentItem()

        return rootView
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val mAdapter = Adapter(activity!!.supportFragmentManager)

        mAdapter.addFragment(getText(R.string.month3).toString(), ScheduleContents.getInstance(3))
        mAdapter.addFragment(getText(R.string.month4).toString(), ScheduleContents.getInstance(4))
        mAdapter.addFragment(getText(R.string.month5).toString(), ScheduleContents.getInstance(5))
        mAdapter.addFragment(getText(R.string.month6).toString(), ScheduleContents.getInstance(6))
        mAdapter.addFragment(getText(R.string.month7).toString(), ScheduleContents.getInstance(7))
        mAdapter.addFragment(getText(R.string.month8).toString(), ScheduleContents.getInstance(8))
        mAdapter.addFragment(getText(R.string.month9).toString(), ScheduleContents.getInstance(9))
        mAdapter.addFragment(getText(R.string.month10).toString(), ScheduleContents.getInstance(10))
        mAdapter.addFragment(getText(R.string.month11).toString(), ScheduleContents.getInstance(11))
        mAdapter.addFragment(getText(R.string.month12).toString(), ScheduleContents.getInstance(12))
        mAdapter.addFragment(getText(R.string.month1).toString(), ScheduleContents.getInstance(1))
        mAdapter.addFragment(getText(R.string.month2).toString(), ScheduleContents.getInstance(2))

        viewPager.adapter = mAdapter
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
        var month = 0

        val mCalendar = Calendar.getInstance()
        when {
            mCalendar.get(Calendar.MONTH) == Calendar.JANUARY -> month = 10
            mCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY -> month = 11
            mCalendar.get(Calendar.MONTH) == Calendar.MARCH -> month = 0
            mCalendar.get(Calendar.MONTH) == Calendar.APRIL -> month = 1
            mCalendar.get(Calendar.MONTH) == Calendar.MAY -> month = 2
            mCalendar.get(Calendar.MONTH) == Calendar.JUNE -> month = 3
            mCalendar.get(Calendar.MONTH) == Calendar.JULY -> month = 4
            mCalendar.get(Calendar.MONTH) == Calendar.AUGUST -> month = 5
            mCalendar.get(Calendar.MONTH) == Calendar.SEPTEMBER -> month = 6
            mCalendar.get(Calendar.MONTH) == Calendar.OCTOBER -> month = 7
            mCalendar.get(Calendar.MONTH) == Calendar.NOVEMBER -> month = 8
            mCalendar.get(Calendar.MONTH) == Calendar.DECEMBER -> month = 9
        }

        mViewPager.currentItem = month
    }

    private inner class Adapter internal constructor(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
        private val mFragments = ArrayList<Fragment>()
        private val mFragmentTitles = ArrayList<String>()

        internal fun addFragment(mTitle: String, mFragment: Fragment) {
            mFragments.add(mFragment)
            mFragmentTitles.add(mTitle)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }
}