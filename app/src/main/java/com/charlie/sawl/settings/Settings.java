package com.charlie.sawl.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.charlie.sawl.R;
import com.charlie.sawl.settings.about.About;
import com.charlie.sawl.settings.mail.Mail;

public class Settings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        Preference detail = findPreference("detail");
        detail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), DetailSettings.class);
                startActivity(intent);
                return true;
            }
        });

        Preference notice = findPreference("notice");
        notice.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), Notice.class);
                startActivity(intent);
                return true;
            }
        });

        Preference about = findPreference("about");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
                return true;
            }
        });

        Preference mail = findPreference("mail");
        mail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                Intent intent = new Intent(getActivity(), Mail.class);
                startActivity(intent);
                return true;
            }
        });
    }
}