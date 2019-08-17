package com.charlie.sawl.timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charlie.sawl.R

class Timetable : Fragment() {

    private lateinit var mAdapter: TimetableAdapter
    private val mTimetableList = ArrayList<TimetableDB>()
    private lateinit var mDBHelper: TimetableDBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_timetable, container, false)

        mDBHelper = TimetableDBHelper(activity!!)
        // 첫 시작시에만 작동할 수 있도록 변경
        //init()
        mTimetableList.clear()
        mTimetableList.addAll(mDBHelper.getAll)

        val mRecyclerView = rootView.findViewById<RecyclerView>(R.id.mRecyclerView)
        mAdapter = TimetableAdapter(mTimetableList, object : TimetableAdapter.MyAdapterListener {
            override fun onEditClicked(v: View, position: Int) {
                val bottomSheetFragment = TimetableDialog.newInstance(mTimetableList[position].subject!!,
                        mTimetableList[position].teacher!!, position)
                bottomSheetFragment.show(activity!!.supportFragmentManager, bottomSheetFragment.tag)
            }
        })
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity!!, 5)
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = mAdapter

        rootView.viewTreeObserver.addOnWindowFocusChangeListener{
            mTimetableList.clear()
            mTimetableList.addAll(mDBHelper.getAll)
            mAdapter.notifyDataSetChanged()
        }

        return rootView
    }

    private fun init() {
        for (i in 1..40) {
            val id = mDBHelper.insert("과목", "선생님")
            val n = mDBHelper.get(id)
            mTimetableList.add(n)
        }
    }
}