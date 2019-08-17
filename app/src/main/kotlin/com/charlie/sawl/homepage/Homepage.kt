package com.charlie.sawl.homepage

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

class Homepage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab, container, false)

        val tabLayout = rootView.findViewById<TabLayout>(R.id.mTabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.notice))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.parent))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val viewPager = rootView.findViewById<ViewPager>(R.id.mViewPager)
        val adapter = HomepageAdapter(activity!!.supportFragmentManager, 2)

        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager.offscreenPageLimit = 2
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager))

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_homepage_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.information -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("공지사항 본문이 보이지 않아요!")
                builder.setMessage("본문이 순수한 텍스트로 되어 있을 경우에만 확인이 가능합니다. 사진, 표 등 뿐만 아니라 특수효과(밑줄 등)가 적용된 텍스트는 확인이 어려울 수 있습니다.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.show()
            }
        }
        return true
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

    inner class HomepageAdapter internal constructor(fm: FragmentManager, private var numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return Notice()
                1 -> return Parent()
            }

            return Notice()
        }

        override fun getCount(): Int {
            return numOfTabs
        }
    }
}