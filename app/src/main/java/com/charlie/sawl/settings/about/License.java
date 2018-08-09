package com.charlie.sawl.settings.about;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.theme.ThemeChanger;
import java.io.IOException;
import java.io.InputStream;

public class License extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.setAppTheme(this);
        setContentView(R.layout.activity_license);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtContent = findViewById(R.id.txtContent);

        AssetManager assetManager = getAssets();

        InputStream input;
        try {
            input = assetManager.open("License.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);

            txtContent.setText(text);
        } catch (IOException ignored) {}
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