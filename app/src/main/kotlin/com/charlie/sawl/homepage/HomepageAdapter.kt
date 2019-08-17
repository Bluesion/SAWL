package com.charlie.sawl.homepage

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.charlie.sawl.R
import com.google.android.material.card.MaterialCardView
import java.util.ArrayList

class HomepageAdapter internal constructor(private val mContext: Activity, private val title: ArrayList<String>?,
                                           private val author: ArrayList<String>?,
                                           private val date: ArrayList<String>?,
                                           private val href: ArrayList<String>?) : RecyclerView.Adapter<HomepageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.row_homepage_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mPage.setOnClickListener {
            val intent = Intent(holder.itemView.context, HomepageActivity::class.java)
            intent.putExtra("title", title!![position])
            intent.putExtra("author", author!![position])
            intent.putExtra("date", date!![position])
            intent.putExtra("URL", href!![position])
            holder.itemView.context.startActivity(intent)
        }
        holder.mTitle.text = title!![position]
        holder.mData.text = String.format(holder.itemView.context.getString(R.string.homepage), author!![position], date!![position])
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItemCount(): Int {
        return title!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mPage: MaterialCardView = view.findViewById(R.id.page)
        var mTitle: TextView = view.findViewById(R.id.title)
        var mData: TextView = view.findViewById(R.id.data)
    }
}