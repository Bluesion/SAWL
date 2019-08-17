package com.charlie.sawl.schedule

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.charlie.sawl.R
import java.util.ArrayList

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    private val mValues = ArrayList<ScheduleInfo>()

    fun addItem(mSchedule: String, mDate: String) {
        val addInfo = ScheduleInfo()

        addInfo.date = mDate
        addInfo.schedule = mSchedule
        addInfo.isHoliday = false
        addInfo.home = false

        mValues.add(addInfo)
    }

    fun addItem(mSchedule: String, mDate: String, home: Boolean, isHoliday: Boolean) {
        val addInfo = ScheduleInfo()

        addInfo.date = mDate
        addInfo.schedule = mSchedule
        addInfo.isHoliday = isHoliday
        addInfo.home = home

        mValues.add(addInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.row_schedule_item, parent, false)

        return ScheduleViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val mInfo = getItemData(position)

        holder.mDate.text = mInfo.date
        holder.mSchedule.text = mInfo.schedule

        if (mInfo.isHoliday && mInfo.home) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.context, R.color.refresh_red))
        } else if (mInfo.isHoliday) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.context, R.color.refresh_red))
        } else if (mInfo.home) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.context, R.color.refresh_blue))
        } else {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.context, R.color.schedule_normal_text))
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun getItemData(position: Int): ScheduleInfo {
        return mValues[position]
    }

    inner class ScheduleViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mDate: TextView = mView.findViewById(R.id.list_item_entry_summary)
        val mSchedule: TextView = mView.findViewById(R.id.list_item_entry_title)
    }

    inner class ScheduleInfo {
        var date: String? = null
        var schedule: String? = null
        var isHoliday: Boolean = false
        var home: Boolean = false
    }
}