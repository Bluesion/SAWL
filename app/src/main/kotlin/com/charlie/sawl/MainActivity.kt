package com.charlie.sawl

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.charlie.sawl.card.Card
import com.charlie.sawl.clock.Clock
import com.charlie.sawl.homepage.Homepage
import com.charlie.sawl.main.Main
import com.charlie.sawl.meal.Meal
import com.charlie.sawl.quickconnect.QuickConnect
//import com.charlie.sawl.reminder.Reminder
import com.charlie.sawl.ruler.Ruler
import com.charlie.sawl.schedule.Schedule
import com.charlie.sawl.settings.Settings
import com.charlie.sawl.test.Test
import com.charlie.sawl.timetable.Timetable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, Main()).commit()
        }

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
            R.id.fragment_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Main()).commit()
                toolbar.setTitle(R.string.nav_1)
            }
            R.id.fragment_meal -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Meal()).commit()
                toolbar.setTitle(R.string.nav_2)
            }
            R.id.fragment_timetable -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Timetable()).commit()
                toolbar.setTitle(R.string.nav_3)
            }
            R.id.fragment_schedule -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Schedule()).commit()
                toolbar.setTitle(R.string.nav_4)
            }
            R.id.fragment_homepage -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Homepage()).commit()
                toolbar.setTitle(R.string.nav_5)
            }
            R.id.fragment_quickconnect -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, QuickConnect()).commit()
                toolbar.setTitle(R.string.nav_6)
            }
            R.id.fragment_clock -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Clock()).commit()
                toolbar.setTitle(R.string.nav_7)
            }
            R.id.fragment_test -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Test()).commit()
                toolbar.setTitle(R.string.nav_8)
            }
            R.id.fragment_ruler -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Ruler()).commit()
                toolbar.setTitle(R.string.nav_9)
            }
            R.id.fragment_card -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Card()).commit()
                toolbar.setTitle(R.string.nav_10)
            }
            R.id.fragment_reminder -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Card()).commit()
                toolbar.setTitle(R.string.nav_11)
            }
            R.id.fragment_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Settings()).commit()
                toolbar.setTitle(R.string.nav_12)
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    public override fun onNewIntent(paramIntent: Intent) {
        intent = paramIntent
        super.onNewIntent(intent)
    }
}