package com.charlie.sawl.homepage

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charlie.sawl.R
import com.charlie.sawl.tools.Tools
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.ArrayList

class Parent : Fragment() {

    private var mTitles: ArrayList<String>? = null
    private var mAuthors: ArrayList<String>? = null
    private var mDates: ArrayList<String>? = null
    private var mHrefs: ArrayList<String>? = null
    private var mAdapter: HomepageAdapter? = null
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private class MyHandler(fragment: Parent): Handler() {
        private val mFragment: WeakReference<Parent> = WeakReference(fragment)

        override fun handleMessage(msg: Message) {
            val fragment = mFragment.get()
            fragment?.handleMessage()
        }
    }

    private fun handleMessage() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_homepage, container, false)

        mTitles = ArrayList()
        mHrefs = ArrayList()
        mAuthors = ArrayList()
        mDates = ArrayList()

        val mRecyclerView = rootView.findViewById<RecyclerView>(R.id.mRecyclerView)
        val horizontalLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mRecyclerView.layoutManager = horizontalLayoutManager
        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_red, R.color.refresh_green,
                R.color.refresh_purple, R.color.refresh_blue)

        if (Tools.isOnline(activity!!.applicationContext)) {
            mSwipeRefreshLayout.setOnRefreshListener { networkTask() }
            networkTask()
        } else {
            mSwipeRefreshLayout.setOnRefreshListener { mSwipeRefreshLayout.isRefreshing = false }
        }

        return rootView
    }

    private fun networkTask() {
        val mHandler = MyHandler(this@Parent)
        object: Thread() {
            override fun run() {
                mHandler.post { mSwipeRefreshLayout.isRefreshing = true }
                try {
                    val doc = Jsoup.connect("http://www.sawl.hs.kr/sys/bbs/board.php?bo_table=030801").get()
                    val titleData = doc.select(".td_subject a")
                    val authorData = doc.select("td:eq(2) span")
                    val dateData = doc.select("td:eq(3)")

                    for (el in titleData) {
                        val href = el.attr("href")
                        val title = el.text()
                        mHrefs!!.add(href)
                        mTitles!!.add(title)
                    }

                    for (el in authorData) {
                        val author = el.text()
                        mAuthors!!.add(author)
                    }

                    for (el in dateData) {
                        val date = el.text()
                        mDates!!.add(date)
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mHandler.post {
                    mAdapter = HomepageAdapter(activity!!, mTitles, mAuthors, mDates, mHrefs)
                    mHandler.sendEmptyMessage(0)
                    mSwipeRefreshLayout.isRefreshing = false
                }

            }
        }.start()
    }
}
