package com.charlie.sawl.homepage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import androidx.appcompat.widget.Toolbar
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.View
import com.charlie.sawl.R
import com.charlie.sawl.tools.Tools
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference

class HomepageActivity : AppCompatActivity() {

    internal lateinit var button: Button
    private lateinit var mTitle: TextView
    private lateinit var mData: TextView
    internal lateinit var mContents: TextView
    internal lateinit var file1: TextView
    internal lateinit var file2: TextView
    internal lateinit var file3: TextView
    internal lateinit var file4: TextView
    internal lateinit var file5: TextView
    internal lateinit var mCardView: MaterialCardView
    internal var content: String? = ""
    internal var filename1 = ""
    internal var filename2 = ""
    internal var filename3 = ""
    internal var filename4 = ""
    internal var filename5 = ""
    internal lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    internal lateinit var `in`: Intent

    private class MyHandler(activity: HomepageActivity) : Handler() {
        private val mActivity: WeakReference<HomepageActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            val activity = mActivity.get()
            activity?.handleMessage()
        }
    }

    private fun handleMessage() {}

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        `in` = intent

        mTitle = findViewById(R.id.mTitle)
        mTitle.text = `in`.getStringExtra("title")

        mData = findViewById(R.id.mData)
        mData.text = String.format(getString(R.string.homepage), `in`.getStringExtra("author")!!, `in`.getStringExtra("date")!!)

        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_red, R.color.refresh_green,
                R.color.refresh_purple, R.color.refresh_blue)

        if (Tools.isOnline(applicationContext)) {
            mContents = findViewById(R.id.mContents)
            file1 = findViewById(R.id.file1)
            file2 = findViewById(R.id.file2)
            file3 = findViewById(R.id.file3)
            file4 = findViewById(R.id.file4)
            file5 = findViewById(R.id.file5)
            mCardView = findViewById(R.id.mCardView)
            networkTask()
            mSwipeRefreshLayout.setOnRefreshListener { networkTask() }
        } else {
            mContents.text = "네트워크를 확인해주세요"
            mSwipeRefreshLayout.setOnRefreshListener { mSwipeRefreshLayout.isRefreshing = false }
        }
    }

    private fun networkTask() {
        val mHandler = MyHandler(this@HomepageActivity)
        object : Thread() {
            override fun run() {
                mHandler.post { mSwipeRefreshLayout.isRefreshing = true }
                try {
                    val doc = Jsoup.connect(`in`.getStringExtra("URL")).get()
                    val rawcontents = doc.select("#wrapper #container #bo_v #bo_v_atc #bo_v_con")
                    val rawfile1 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(0) a")
                    val rawfile2 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(1) a")
                    val rawfile3 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(2) a")
                    val rawfile4 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(3) a")
                    val rawfile5 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(4) a")

                    var contentText = rawcontents.html()
                    contentText = Jsoup.parse(contentText.replace("(?i)<p[^>]*>".toRegex(), "br2n")).text()
                    content = contentText.replace("br2n", "\n")

                    for (el in rawfile1) {
                        val fileData1 = el.attr("href")
                        val fileTitle1 = el.text()
                        filename1 = "<a href=\"$fileData1\">$fileTitle1</a>"
                    }

                    for (el in rawfile2) {
                        val fileData2 = el.attr("href")
                        val fileTitle2 = el.text()
                        filename2 = "<a href=\"$fileData2\">$fileTitle2</a>"
                    }

                    for (el in rawfile3) {
                        val fileData3 = el.attr("href")
                        val fileTitle3 = el.text()
                        filename3 = "<a href=\"$fileData3\">$fileTitle3</a>"
                    }

                    for (el in rawfile4) {
                        val fileData4 = el.attr("href")
                        val fileTitle4 = el.text()
                        filename4 = "<a href=\"$fileData4\">$fileTitle4</a>"
                    }

                    for (el in rawfile5) {
                        val fileData5 = el.attr("href")
                        val fileTitle5 = el.text()
                        filename5 = "<a href=\"$fileData5\">$fileTitle5</a>"
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mHandler.post {
                    if (content == "" || content == null || content == " ") {
                        mContents.text = "<첨부파일 참조>"
                    } else {
                        mContents.text = content
                    }
                    if (filename1 != "") {
                        mCardView.visibility = View.VISIBLE
                        file1.text = filename1.toSpanned()
                        file1.movementMethod = LinkMovementMethod.getInstance()
                    }
                    if (filename2 != "") {
                        file2.visibility = View.VISIBLE
                        file2.text = filename2.toSpanned()
                        file2.movementMethod = LinkMovementMethod.getInstance()
                    }
                    if (filename3 != "") {
                        file3.visibility = View.VISIBLE
                        file3.text = filename3.toSpanned()
                        file3.movementMethod = LinkMovementMethod.getInstance()
                    }
                    if (filename4 != "") {
                        file4.visibility = View.VISIBLE
                        file4.text = filename4.toSpanned()
                        file4.movementMethod = LinkMovementMethod.getInstance()
                    }
                    if (filename5 != "") {
                        file5.visibility = View.VISIBLE
                        file5.text = filename5.toSpanned()
                        file5.movementMethod = LinkMovementMethod.getInstance()
                    }
                    mHandler.sendEmptyMessage(0)
                    mSwipeRefreshLayout.isRefreshing = false
                }

            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_homepage_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.go -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(`in`.getStringExtra("URL"))))
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun String.toSpanned(): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
    }
}