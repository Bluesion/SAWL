package com.charlie.sawl.clock

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charlie.sawl.R
import com.charlie.sawl.clock.timer.Timer

class Clock : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab, container, false)

        val tabLayout = rootView.findViewById<TabLayout>(R.id.mTabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.timer))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.stopwatch))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val mViewPager = rootView.findViewById<ViewPager>(R.id.mViewPager)
        val adapter = ContentsAdapter(activity!!.supportFragmentManager, 2)

        mViewPager.adapter = adapter
        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager))

        return rootView
    }

    private fun onTabSelectedListener(pager: ViewPager): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
    }

    inner class ContentsAdapter internal constructor(fm: FragmentManager, private var numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return Timer()
                1 -> return StopWatch()
            }
            return Timer()
        }

        override fun getCount(): Int {
            return numOfTabs
        }
    }
}