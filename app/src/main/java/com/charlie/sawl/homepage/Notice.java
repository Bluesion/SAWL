package com.charlie.sawl.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.charlie.sawl.R;
import com.charlie.sawl.tools.Tools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class Notice extends Fragment {

    private ArrayList<String> mTitle, mAuthor, mDate, mHref;
    private HomepageAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mListView;

    private final MyHandler handler = new MyHandler(Notice.this);
    private static class MyHandler extends Handler {
        private final WeakReference<Notice> mFragment;
        private MyHandler(Notice fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            Notice fragment = mFragment.get();
            if (fragment != null) {
                fragment.handleMessage();
            }
        }
    }
    private void handleMessage() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);

        mListView = rootView.findViewById(R.id.mListView);
        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_red, R.color.refresh_green,
                R.color.refresh_purple, R.color.refresh_blue);

        if (Tools.isOnline(Objects.requireNonNull(getActivity()).getApplicationContext())) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    networkTask();
                }
            });
            networkTask();
        } else {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }

        return rootView;
    }

    private AdapterView.OnItemClickListener goToWebPage = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long id) {
            try {
                String title = mTitle.get(pos);
                String author = mAuthor.get(pos);
                String date = mDate.get(pos);
                String url = mHref.get(pos);

                Intent intent = new Intent(getActivity(), HomepageActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("author", author);
                intent.putExtra("date", date);
                intent.putExtra("URL", url);
                startActivity(intent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    };

    private void networkTask() {
        final MyHandler mHandler = new MyHandler(Notice.this);
        new Thread() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
                try {
                    mTitle = new ArrayList<>();
                    mHref = new ArrayList<>();
                    mAuthor = new ArrayList<>();
                    mDate = new ArrayList<>();
                    Document doc = Jsoup.connect("http://www.sawl.hs.kr/sys/bbs/board.php?bo_table=030601").get();
                    Elements rawdata = doc.select(".td_subject a");
                    Elements rawauthordata = doc.select("td:eq(2) span");
                    Elements rawdatedata = doc.select("td:eq(3)");

                    for (Element el : rawdata) {
                        String href = el.attr("href");
                        String titles2 = el.text();
                        mHref.add(href);
                        mTitle.add(titles2);
                    }

                    for (Element el : rawauthordata) {
                        String authordata = el.text();
                        mAuthor.add(authordata);
                    }

                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        mDate.add(datedata);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        mAdapter = new HomepageAdapter(getActivity(), mTitle, mAuthor, mDate);
                        mListView.setAdapter(mAdapter);
                        mListView.setOnItemClickListener(goToWebPage);
                        handler.sendEmptyMessage(0);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }.start();
    }
}
