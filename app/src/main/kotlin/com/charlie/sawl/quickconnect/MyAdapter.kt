package com.charlie.sawl.quickconnect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charlie.sawl.R
import java.util.ArrayList

class MyAdapter(private val context: Context, arrayList: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var arrayList = ArrayList<String>()

    init {
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_quickconnect_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val index = arrayList[position].indexOf("/")
        val text = arrayList[position].substring(0, index)
        val number = arrayList[position].substring(index + 1, arrayList[position].length)
        holder.text.text = text
        holder.number.text = number
        holder.phoneBook.setOnClickListener {
            val tel = "tel:$number"
            val intent = Intent("android.intent.action.DIAL", Uri.parse(tel))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val phoneBook: LinearLayout = itemView.findViewById(R.id.phoneBook)
        internal var text: TextView = itemView.findViewById(R.id.text)
        internal var number: TextView = itemView.findViewById(R.id.number)
    }
}