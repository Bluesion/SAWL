package com.charlie.sawl.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.charlie.sawl.R
import com.charlie.sawl.tools.Tools

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        val sharedPrefs = activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val dark = findPreference<Preference>("dark")
        dark!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val on = (preference as SwitchPreference).isChecked
            if (on) {
                editor.putBoolean("dark", true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor.putBoolean("dark", false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            editor.apply()
            true
        }

        val saver = findPreference<Preference>("saver")
        saver!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val on = (preference as SwitchPreference).isChecked
            if (on) {
                editor.putBoolean("saver", true)
            } else {
                editor.putBoolean("saver", false)
            }
            editor.apply()
            Tools.restart(activity!!, 0)
            true
        }

        val about = findPreference<Preference>("about")
        about!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            startActivity(Intent(activity, AboutActivity::class.java))
            true
        }
    }
}