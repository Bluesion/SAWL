package com.charlie.sawl.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.charlie.sawl.BuildConfig
import com.charlie.sawl.R
import com.charlie.sawl.tools.Tools
import com.google.android.material.button.MaterialButton
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference

class AboutActivity : AppCompatActivity() {

    internal var versionName = BuildConfig.VERSION_NAME
    internal lateinit var latestVersion: TextView
    internal lateinit var latestVersionString: String

    private val handler = MyHandler(this@AboutActivity)
    private class MyHandler (activity: AboutActivity) : Handler() {
        private val mActivity: WeakReference<AboutActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            val activity = mActivity.get()
            activity?.handleMessage()
        }
    }

    private fun handleMessage() {}

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val currentVersion = findViewById<TextView>(R.id.currentVersion)
        currentVersion.text = versionName

        val license = findViewById<MaterialButton>(R.id.license)
        license.setOnClickListener {
            val intent = Intent(this@AboutActivity, LicenseActivity::class.java)
            startActivity(intent)
        }

        val updateButton = findViewById<MaterialButton>(R.id.update)
        updateButton.setOnClickListener {
            val goPlayStore = Intent(Intent.ACTION_VIEW)
            goPlayStore.data = Uri.parse("market://details?id=" + applicationContext.packageName)
            startActivity(goPlayStore)
        }

        latestVersion = findViewById(R.id.latestVersion)
        if (Tools.isOnline(applicationContext)) {
            networkTask()
        } else {
            latestVersion.text = versionName
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun networkTask() {
        val mHandler = MyHandler(this@AboutActivity)
        object : Thread() {
            override fun run() {
                mHandler.post { }
                try {
                    val doc = Jsoup.connect("http://gpillusion.tistory.com/9").get()
                    val version = doc.select("#mArticle div.skin_view div.area_view div.tt_article_useless_p_margin p")

                    for (el in version) {
                        latestVersionString = el.text().trim { it <= ' ' }
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mHandler.post {
                    if (latestVersionString == "") {
                        latestVersion.text = versionName
                    } else {
                        latestVersion.text = latestVersionString
                    }
                    handler.sendEmptyMessage(0)
                }
            }
        }.start()
    }
}