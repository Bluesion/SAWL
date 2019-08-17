package com.charlie.sawl.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charlie.sawl.R

class TimetableAdapter(private val timetableList: List<TimetableDB>, val onClickListener: MyAdapterListener): RecyclerView.Adapter<TimetableAdapter.MyViewHolder>() {

    private lateinit var layout: LinearLayout

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var subject: TextView = view.findViewById(R.id.subject)
        var teacher: TextView = view.findViewById(R.id.teacher)

        init {
            layout.setOnClickListener { v -> onClickListener.onEditClicked(v, adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_timetable_item, parent, false)
        layout = itemView.findViewById(R.id.timetable)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val timetable = timetableList[position]

        holder.subject.text = timetable.subject
        holder.teacher.text = timetable.teacher
    }

    override fun getItemCount(): Int {
        return timetableList.size
    }

    interface MyAdapterListener {
        fun onEditClicked(v: View, position: Int)
    }
}