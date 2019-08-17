package com.charlie.sawl.meal.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.charlie.sawl.R
import com.charlie.sawl.meal.MealTool
import java.util.ArrayList

class DinnerAdapter(private var mContext: Context?) : RecyclerView.Adapter<DinnerAdapter.ViewHolder>() {
    private val mListData = ArrayList<MealListData>()

    fun addItem(mCalender: String, mDayOfTheWeek: String, mDinner: String, isToday: Boolean) {
        val addItemInfo = MealListData()
        addItemInfo.mCalender = mCalender
        addItemInfo.mDayOfTheWeek = mDayOfTheWeek
        addItemInfo.mDinner = mDinner
        addItemInfo.isToday = isToday

        mListData.add(addItemInfo)
    }

    fun clearData() {
        mListData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.row_meal_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mData = mListData[position]

        val mCalender = mData.mCalender
        val mDayOfTheWeek = mData.mDayOfTheWeek
        var mDinner = mData.mDinner

        if (MealTool.mStringCheck(mDinner)) {
            mData.mDinner = mContext!!.resources.getString(R.string.no_data_dinner)
            mDinner = mData.mDinner
        }

        holder.mCalender.text = mCalender
        holder.mDayOfTheWeek.text = mDayOfTheWeek
        holder.mDinner.text = mDinner
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mCalender: TextView = view.findViewById(R.id.mCalender)
        var mDayOfTheWeek: TextView = view.findViewById(R.id.mDayOfTheWeek)
        var mDinner: TextView = view.findViewById(R.id.mMeal)

        init {
            view.tag = view
        }
    }
}