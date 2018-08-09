package com.charlie.sawl.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.theme.ThemeChanger;
import com.charlie.sawl.tools.Tools;

public class DetailSettings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.setAppTheme(this);
        setContentView(R.layout.activity_detail_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean dark = sharedPrefs.getBoolean("dark", true);
        SwitchCompat mThemeSwitch = findViewById(R.id.theme_switch);
        if (dark) {
            mThemeSwitch.setChecked(true);
        }
        mThemeSwitch.setOnCheckedChangeListener(this);

        Boolean save = sharedPrefs.getBoolean("save", true);
        SwitchCompat mDataSwitch = findViewById(R.id.data_switch);
        if (save) {
            mDataSwitch.setChecked(true);
        } else {
            mDataSwitch.setChecked(false);
        }
        mDataSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.theme_switch:
                if (isChecked) {
                    SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("dark", true);
                    editor.apply();
                    Tools.restart(DetailSettings.this, 0);
                } else {
                    SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("dark", false);
                    editor.apply();
                    Tools.restart(DetailSettings.this, 0);
                }
                break;
            case R.id.data_switch:
                if (isChecked) {
                    SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("save", true);
                    editor.apply();
                    Tools.restart(DetailSettings.this, 0);
                } else {
                    SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("save", false);
                    editor.apply();
                    Tools.restart(DetailSettings.this, 0);
                }
                break;
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
}