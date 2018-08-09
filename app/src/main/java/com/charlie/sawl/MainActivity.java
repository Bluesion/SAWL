package com.charlie.sawl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.charlie.sawl.fragment.Card;
import com.charlie.sawl.homepage.Homepage;
import com.charlie.sawl.main.Main;
import com.charlie.sawl.meal.Meal;
import com.charlie.sawl.memo.Memo;
import com.charlie.sawl.fragment.QuickConnect;
import com.charlie.sawl.schedule.Schedule;
import com.charlie.sawl.settings.Settings;
import com.charlie.sawl.settings.theme.ThemeChanger;
import com.charlie.sawl.timetable.TimeTable;
import com.charlie.sawl.tools.Tools;
import com.charlie.sawl.updates.Update01;
import com.charlie.sawl.updates.Update02;
import com.charlie.sawl.updates.Update03;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    private FrameLayout normal, card;
    private AppCompatTextView counter, temperature, temperatureText, dust, dustText;
    private String temperatureString = "", dustString = "";

    private MyHandler handler = new MyHandler(MainActivity.this);
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivity;
        private MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage();
            }
        }
    }

    private void handleMessage() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.setAppTheme(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.cardtoolbar);
        setSupportActionBar(toolbar);

        counter = findViewById(R.id.countdown);
        counter.setText(getDDayString());

        normal = findViewById(R.id.normalbar);
        card = findViewById(R.id.cardbar);
        normal.setVisibility(View.GONE);
        card.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Main()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences updates = getSharedPreferences("updates", MODE_PRIVATE);
        SharedPreferences.Editor editor = updates.edit();
        Boolean update01 = updates.getBoolean("e", true);
        Boolean update02 = updates.getBoolean("f", true);
        if (update01) {
            Intent update = new Intent(MainActivity.this, Update01.class);
            startActivity(update);
            editor.putBoolean("e", false);
            editor.apply();
        } else if (update02) {
            Intent update = new Intent(MainActivity.this, Update02.class);
            startActivity(update);
            editor.putBoolean("f", false);
            editor.apply();
        } else if (update01 && update02) {
            Intent update = new Intent(MainActivity.this, Update03.class);
            startActivity(update);
            editor.putBoolean("g", false);
            editor.apply();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        temperatureText = headerView.findViewById(R.id.temperatureText);
        temperature = headerView.findViewById(R.id.temperature);
        dustText = headerView.findViewById(R.id.dustText);
        dust = headerView.findViewById(R.id.dust);

        SharedPreferences sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean save = sharedPrefs.getBoolean("save", true);

        if (save) {
            if (Tools.isWifi(getApplicationContext())) {
                networkTask();
            } else {
                temperatureText.setVisibility(View.GONE);
                temperature.setText("환영합니다!");
                dustText.setVisibility(View.GONE);
                dust.setText("Welcome SAWLian!");
            }
        } else {
            networkTask();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        if (id == R.id.fragment_main) {
            normal.setVisibility(View.GONE);
            card.setVisibility(View.VISIBLE);
            toolbar = findViewById(R.id.cardtoolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            counter.setText(getDDayString());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Main()).commit();
        } else if (id == R.id.fragment_meal) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Meal()).commit();
        } else if (id == R.id.fragment_timetable) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TimeTable()).commit();
        } else if (id == R.id.fragment_schedule) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Schedule()).commit();
        } else if (id == R.id.fragment_homepage) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Homepage()).commit();
        } else if (id == R.id.fragment_quickconnect) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new QuickConnect()).commit();
        } else if (id == R.id.fragment_card) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Card()).commit();
        } else if (id == R.id.fragment_memo) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Memo()).commit();
        } else if (id == R.id.fragment_settings) {
            setupToolbar();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Settings()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void networkTask() {
        final MyHandler mHandler = new MyHandler(MainActivity.this);
        new Thread() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                    }
                });
                try {
                    Document doc = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EA%B2%BD%EA%B8%B0%EB%8F%84+%EC%88%98%EC%9B%90%EC%8B%9C+%EC%98%81%ED%86%B5%EA%B5%AC+%EC%9D%B4%EC%9D%98%EB%8F%99+%EB%82%A0%EC%94%A8").get();
                    String sign = "span.blind";
                    String detail = "span.num";
                    doc.select(sign).remove();
                    doc.select(detail).remove();
                    Elements temperatureText = doc.select("#main_pack div.sc.cs_weather._weather div div.weather_box div.weather_area._mainArea div.today_area._mainTabContent div.main_info div.info_data p.info_temperature");
                    Elements dustText = doc.select("#main_pack div.sc.cs_weather._weather div div.weather_box div.weather_area._mainArea div.today_area._mainTabContent div.sub_info div.detail_box dl.indicator dd.lv2");

                    for (Element el : temperatureText) {
                        temperatureString = el.text();
                    }

                    for (Element el : dustText) {
                        dustString = el.text();
                    }

                } catch (IOException ignored) {}

                mHandler.post(new Runnable() {
                    public void run() {
                        if (temperatureString.isEmpty() | temperatureString.equals("")) {
                            temperatureText.setVisibility(View.GONE);
                            temperature.setText("환영합니다!");
                        } else {
                            temperature.setText(temperatureString);
                        }
                        if (dustString.isEmpty() | dustString.equals("")) {
                            dustText.setVisibility(View.GONE);
                            dust.setText("Welcome SAWLian!");
                        } else {
                            dust.setText(dustString);
                        }
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }

    public void setupToolbar() {
        normal.setVisibility(View.VISIBLE);
        card.setVisibility(View.GONE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private String getDDayString(){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(2018, Calendar.NOVEMBER, 15, 0, 0, 0);

        int diffDays = (int) ((mCalendar.getTimeInMillis() - System.currentTimeMillis()) / (86400000));

        return String.format(Locale.KOREA, "%d", diffDays+1);
    }

    public void onNewIntent(Intent paramIntent) {
        setIntent(paramIntent);
    }
}