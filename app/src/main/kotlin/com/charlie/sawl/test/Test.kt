package com.charlie.sawl.test

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.charlie.sawl.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Test : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_test, container, false)

        val bar = rootView.findViewById<BottomAppBar>(R.id.bar)
        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(bar)
        }

        val fab = rootView.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(activity!!, AddEditActivity::class.java))
        }

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_test, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.school -> {
                Toast.makeText(activity!!, "내신", Toast.LENGTH_SHORT).show()
            }
            R.id.csat -> {
                Toast.makeText(activity!!, "수능/모의고사", Toast.LENGTH_SHORT).show()
            }
            R.id.etc -> {
                Toast.makeText(activity!!, "그 외", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}