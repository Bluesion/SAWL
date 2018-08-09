package com.charlie.sawl.settings.theme;

import android.app.Activity;
import android.content.SharedPreferences;
import com.charlie.sawl.R;

public class ThemeChanger {
    public static void setAppTheme(Activity a) {
        SharedPreferences theme = a.getSharedPreferences("settings", Activity.MODE_PRIVATE);
        Boolean dark = theme.getBoolean("dark", true);
        if (dark) {
            a.setTheme(R.style.DarkTheme);
        } else {
            a.setTheme(R.style.AppTheme);
        }
    }
}