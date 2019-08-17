package com.charlie.sawl.quickconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charlie.sawl.R
import com.google.android.material.tabs.TabLayout

class QuickConnect : Fragment() {

    private lateinit var mAdapter: MyAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var first: ArrayList<String>
    private lateinit var second: ArrayList<String>
    private lateinit var third: ArrayList<String>
    private lateinit var dorm: ArrayList<String>
    private lateinit var etc: ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_quickconnect, container, false)

        first = ArrayList()
        first.add("1학년 부장 (대표 연락처)/031-259-0610")
        first.add("1-1 담임/031-259-0611")
        first.add("1-2 담임/031-259-0612")
        first.add("1-3 담임/031-259-0613")
        first.add("1-4 담임/031-259-0614")
        first.add("1-5 담임/031-259-0615")
        first.add("1-6 담임/031-259-0616")
        first.add("1-7 담임/031-259-0617")
        first.add("1-8 담임/031-259-0618")

        second = ArrayList()
        second.add("2학년 부장 (대표 연락처)/031-259-0620")
        second.add("2-1 담임/031-259-0621")
        second.add("2-2 담임/031-259-0622")
        second.add("2-3 담임/031-259-0623")
        second.add("2-4 담임/031-259-0624")
        second.add("2-5 담임/031-259-0625")
        second.add("2-6 담임/031-259-0626")
        second.add("2-7 담임/031-259-0627")
        second.add("2-8 담임/031-259-0628")

        third = ArrayList()
        third.add("3학년 부장 (대표 연락처)/031-259-0630")
        third.add("3-1 담임/031-259-0631")
        third.add("3-2 담임/031-259-0632")
        third.add("3-3 담임/031-259-0633")
        third.add("3-4 담임/031-259-0634")
        third.add("3-5 담임/031-259-0635")
        third.add("3-6 담임/031-259-0636")
        third.add("3-7 담임/031-259-0637")
        third.add("3-8 담임/031-259-0638")

        dorm = ArrayList()
        dorm.add("사감실 (대표 연락처)/031-259-0642")
        dorm.add("기숙사부/031-259-0640")

        etc = ArrayList()
        etc.add("행정실/031-259-0502")
        etc.add("보건실/031-259-0555")
        etc.add("입학홍보부/031-259-0560")
        etc.add("급식실/031-259-0540")
        etc.add("도서실/031-259-0660")
        etc.add("상담실/031-259-0671")

        tabLayout = rootView.findViewById(R.id.mTabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("1학년"))
        tabLayout.addTab(tabLayout.newTab().setText("2학년"))
        tabLayout.addTab(tabLayout.newTab().setText("3학년"))
        tabLayout.addTab(tabLayout.newTab().setText("기숙사"))
        tabLayout.addTab(tabLayout.newTab().setText("그 외"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.addOnTabSelectedListener(onTabSelectedListener())

        mAdapter = MyAdapter(activity!!, first)

        val horizontalLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mRecyclerView = rootView.findViewById(R.id.mRecyclerView)
        mRecyclerView.layoutManager = horizontalLayoutManager
        mRecyclerView.adapter = mAdapter

        return rootView
    }

    private fun onTabSelectedListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> mAdapter = MyAdapter(activity!!, first)
                    1 -> mAdapter = MyAdapter(activity!!, second)
                    2 -> mAdapter = MyAdapter(activity!!, third)
                    3 -> mAdapter = MyAdapter(activity!!, dorm)
                    4 -> mAdapter = MyAdapter(activity!!, etc)
                }
                mRecyclerView.adapter = mAdapter
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
    }
}