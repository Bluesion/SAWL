package com.charlie.sawl.settings.mail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.theme.ThemeChanger;
import com.charlie.sawl.tools.Tools;
import java.lang.ref.WeakReference;

public class Mail extends AppCompatActivity {

    AppCompatEditText editSender, editTextMessage;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final MyHandler handler = new MyHandler(Mail.this);
    private static class MyHandler extends Handler {
        private final WeakReference<Mail> mActivity;
        private MyHandler(Mail activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Mail activity = mActivity.get();
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
        setContentView(R.layout.activity_mail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editSender = findViewById(R.id.editSender);
        editTextMessage = findViewById(R.id.editTextMessage);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);

        FloatingActionButton mFab = findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Tools.isOnline(getApplicationContext())) {
                    sendMail();
                } else {
                    Toast.makeText(getApplicationContext(), "네트워크에 연결해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMail() {
        final MyHandler mHandler = new MyHandler(Mail.this);
        new Thread() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setEnabled(true);
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
                try {
                    String message = "답변 받을 이메일:\n" + editSender.getText().toString().trim() + "\n\n\n작성 내용:\n" + editTextMessage.getText().toString().trim();
                    GMailSender sender = new GMailSender("ID", "PW");
                    try {
                        sender.sendMail("SAWL 애플리케이션", message, "test@gmail.com", "dnjscjf098@gmail.com");
                    } catch (Exception ignored) {}
                } catch (Exception ignored) {}

                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "정상적으로 메일이 전송되었습니다", Toast.LENGTH_SHORT).show();
                        editSender.setText("");
                        editTextMessage.setText("");
                        mSwipeRefreshLayout.setEnabled(false);
                        mSwipeRefreshLayout.setRefreshing(false);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }
}