package com.charlie.sawl.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.theme.ThemeChanger;
import com.charlie.sawl.tools.Tools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class HomepageActivity extends AppCompatActivity {

    AppCompatButton button;
    AppCompatTextView mTitle, mData, mContents, file1 ,file2, file3, file4, file5;
    CardView mCardView;
    String content = "", filename1 = "", filename2 = "", filename3 = "", filename4 = "", filename5 = "";
    SwipeRefreshLayout mSwipeRefreshLayout;
    Intent in;
    private final MyHandler handler = new MyHandler(HomepageActivity.this);
    private static class MyHandler extends Handler {
        private final WeakReference<HomepageActivity> mActivity;
        private MyHandler(HomepageActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HomepageActivity activity = mActivity.get();
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
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        in = getIntent();

        mTitle = findViewById(R.id.mTitle);
        mTitle.setText(in.getStringExtra("title"));

        mData = findViewById(R.id.mData);
        mData.setText(in.getStringExtra("author") + " · " + in.getStringExtra("date"));

        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh_red),
                getResources().getColor(R.color.refresh_green),
                getResources().getColor(R.color.refresh_purple),
                getResources().getColor(R.color.refresh_blue));

        if (Tools.isOnline(getApplicationContext())) {
            mContents = findViewById(R.id.mContents);
            file1 = findViewById(R.id.file1);
            file2 = findViewById(R.id.file2);
            file3 = findViewById(R.id.file3);
            file4 = findViewById(R.id.file4);
            file5 = findViewById(R.id.file5);
            mCardView = findViewById(R.id.mCardView);
            button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(in.getStringExtra("URL"))));
                }
            });
            networkTask();
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    networkTask();
                }
            });
        } else {
            mContents.setText("네트워크를 확인해주세요");
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void networkTask() {
        final MyHandler mHandler = new MyHandler(HomepageActivity.this);
        new Thread() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
                try {
                    Document doc = Jsoup.connect(in.getStringExtra("URL")).get();
                    Elements rawcontents = doc.select("#wrapper #container #bo_v #bo_v_atc #bo_v_con");
                    Elements rawfile1 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(0) a");
                    Elements rawfile2 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(1) a");
                    Elements rawfile3 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(2) a");
                    Elements rawfile4 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(3) a");
                    Elements rawfile5 = doc.select("#wrapper #container #bo_v #bo_v_file ul li:eq(4) a");

                    for (Element el : rawcontents) {
                        String contentText = rawcontents.html();
                        contentText = Jsoup.parse(contentText.replaceAll("(?i)<p[^>]*>", "br2n")).text();
                        content = contentText.replace("br2n", "\n");
                    }

                    for (Element el : rawfile1) {
                        String filedata1 = el.attr("href");
                        String filetitle1 = el.text();
                        filename1 = "<a href=\"" + filedata1 + "\">" + filetitle1 + "</a>";
                    }

                    for (Element el : rawfile2) {
                        String filedata2 = el.attr("href");
                        String filetitle2 = el.text();
                        filename2 = "<a href=\"" + filedata2 + "\">" + filetitle2 + "</a>";
                    }

                    for (Element el : rawfile3) {
                        String filedata3 = el.attr("href");
                        String filetitle3 = el.text();
                        filename3 = "<a href=\"" + filedata3 + "\">" + filetitle3 + "</a>";
                    }

                    for (Element el : rawfile4) {
                        String filedata4 = el.attr("href");
                        String filetitle4 = el.text();
                        filename4 = "<a href=\"" + filedata4 + "\">" + filetitle4 + "</a>";
                    }

                    for (Element el : rawfile5) {
                        String filedata5 = el.attr("href");
                        String filetitle5 = el.text();
                        filename5 = "<a href=\"" + filedata5 + "\">" + filetitle5 + "</a>";
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        if (content.equals("") || content == null || content.equals(" ")) {
                            mContents.setText("<첨부파일 참조>");
                        } else {
                            mContents.setText(content);
                        }
                        if (!Objects.equals(filename1, "")) {
                            mCardView.setVisibility(View.VISIBLE);
                            file1.setText(Html.fromHtml(filename1));
                            file1.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        if (!Objects.equals(filename2, "")) {
                            file2.setVisibility(View.VISIBLE);
                            file2.setText(Html.fromHtml(filename2));
                            file2.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        if (!Objects.equals(filename3, "")) {
                            file3.setVisibility(View.VISIBLE);
                            file3.setText(Html.fromHtml(filename3));
                            file3.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        if (!Objects.equals(filename4, "")) {
                            file4.setVisibility(View.VISIBLE);
                            file4.setText(Html.fromHtml(filename4));
                            file4.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        if (!Objects.equals(filename5, "")) {
                            file5.setVisibility(View.VISIBLE);
                            file5.setText(Html.fromHtml(filename5));
                            file5.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        handler.sendEmptyMessage(0);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }.start();
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}