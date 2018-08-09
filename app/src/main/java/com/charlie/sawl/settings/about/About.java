package com.charlie.sawl.settings.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.charlie.sawl.BuildConfig;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.theme.ThemeChanger;
import com.charlie.sawl.tools.Tools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class About extends AppCompatActivity {

    String versionName = BuildConfig.VERSION_NAME;
    AppCompatTextView latestVersion;
    String latestVersionString;

    private final MyHandler handler = new MyHandler(About.this);
    private static class MyHandler extends Handler {
        private final WeakReference<About> mActivity;
        private MyHandler(About activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            About activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage();
            }
        }
    }

    private void handleMessage() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.setAppTheme(this);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatTextView currentVersion = findViewById(R.id.currentVersion);
        currentVersion.setText(versionName);

        AppCompatButton license = findViewById(R.id.license);
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(About.this, License.class);
                startActivity(intent);
            }
        });

        AppCompatButton updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goPlayStore = new Intent(Intent.ACTION_VIEW);
                goPlayStore.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                startActivity(goPlayStore);
            }
        });

        latestVersion = findViewById(R.id.latestVersion);
        if (Tools.isOnline(getApplicationContext())) {
            networkTask();
        } else {
            latestVersion.setText(versionName);
        }
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void networkTask() {
        final MyHandler mHandler = new MyHandler(About.this);
        new Thread() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {}
                });
                try {
                    Document doc = Jsoup.connect("http://gpillusion.tistory.com/9").get();
                    Elements version = doc.select("#mArticle div.skin_view div.area_view div.tt_article_useless_p_margin p");

                    for (Element el : version) {
                        latestVersionString = el.text().trim();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        if (latestVersionString.equals("")) {
                            latestVersion.setText(versionName);
                        } else {
                            latestVersion.setText(latestVersionString);
                        }
                        handler.sendEmptyMessage(0);
                    }
                });
            }
        }.start();
    }
}