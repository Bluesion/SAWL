package com.charlie.sawl.updates;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.charlie.sawl.R;
import com.charlie.sawl.tools.Tools;
import java.io.File;

public class Update01 extends AppCompatActivity {

    public String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SAWL/";
    public String mPreferencePath = "/data/data/com.charlie.sawl/shared_prefs/";
    public String mDatabasePath = "/data/data/com.charlie.sawl/databases/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_01);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new File(mFilePath + "MealRate.db").delete();
                new File(mFilePath + "MealRate.db-journal").delete();

                new File(mPreferencePath + "montable1.xml").delete();
                new File(mPreferencePath + "montable2.xml").delete();
                new File(mPreferencePath + "montable3.xml").delete();
                new File(mPreferencePath + "montable4.xml").delete();
                new File(mPreferencePath + "montable5.xml").delete();
                new File(mPreferencePath + "montable6.xml").delete();
                new File(mPreferencePath + "montable7.xml").delete();
                new File(mPreferencePath + "montable8.xml").delete();

                new File(mPreferencePath + "tuetable1.xml").delete();
                new File(mPreferencePath + "tuetable2.xml").delete();
                new File(mPreferencePath + "tuetable3.xml").delete();
                new File(mPreferencePath + "tuetable4.xml").delete();
                new File(mPreferencePath + "tuetable5.xml").delete();
                new File(mPreferencePath + "tuetable6.xml").delete();
                new File(mPreferencePath + "tuetable7.xml").delete();
                new File(mPreferencePath + "tuetable8.xml").delete();

                new File(mPreferencePath + "wedtable1.xml").delete();
                new File(mPreferencePath + "wedtable2.xml").delete();
                new File(mPreferencePath + "wedtable3.xml").delete();
                new File(mPreferencePath + "wedtable4.xml").delete();
                new File(mPreferencePath + "wedtable5.xml").delete();
                new File(mPreferencePath + "wedtable6.xml").delete();
                new File(mPreferencePath + "wedtable7.xml").delete();
                new File(mPreferencePath + "wedtable8.xml").delete();

                new File(mPreferencePath + "thutable1.xml").delete();
                new File(mPreferencePath + "thutable2.xml").delete();
                new File(mPreferencePath + "thutable3.xml").delete();
                new File(mPreferencePath + "thutable4.xml").delete();
                new File(mPreferencePath + "thutable5.xml").delete();
                new File(mPreferencePath + "thutable6.xml").delete();
                new File(mPreferencePath + "thutable7.xml").delete();
                new File(mPreferencePath + "thutable8.xml").delete();

                new File(mPreferencePath + "fritable1.xml").delete();
                new File(mPreferencePath + "fritable2.xml").delete();
                new File(mPreferencePath + "fritable3.xml").delete();
                new File(mPreferencePath + "fritable4.xml").delete();
                new File(mPreferencePath + "fritable5.xml").delete();
                new File(mPreferencePath + "fritable6.xml").delete();
                new File(mPreferencePath + "fritable7.xml").delete();
                new File(mPreferencePath + "fritable8.xml").delete();

                new File(mPreferencePath + "theme.xml").delete();
                new File(mPreferencePath + "student_info.xml").delete();
                new File(mPreferencePath + "pref.xml").delete();

                Tools.restart(Update01.this, 0);
            }
        }, 5000);
    }
}