package com.charlie.sawl.updates;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.charlie.sawl.R;
import com.charlie.sawl.tools.Tools;
import java.io.File;

public class Update02 extends AppCompatActivity {

    String mDatabasePath = "/data/data/com.charlie.sawl/databases/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_02);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new File(mDatabasePath + "memo.db").delete();
                new File(mDatabasePath + "memo.db-journal").delete();

                Tools.restart(Update02.this, 0);
            }
        }, 5000);
    }
}