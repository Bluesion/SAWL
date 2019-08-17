package com.charlie.sawl.tools

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Preference {
    private var mPref: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    constructor(mContext: Context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext)
        mEditor = mPref!!.edit()
    }

    constructor(mContext: Context, prefName: String) {
        mPref = mContext.getSharedPreferences(prefName, 0)
        mEditor = mPref!!.edit()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mPref!!.getBoolean(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return mPref!!.getInt(key, defValue)
    }

    fun getString(key: String, defValue: String?): String? {
        return mPref!!.getString(key, defValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        mEditor!!.putBoolean(key, value).commit()
    }

    fun putInt(key: String, value: Int) {
        mEditor!!.putInt(key, value).commit()
    }

    fun putString(key: String, value: String) {
        mEditor!!.putString(key, value).commit()
    }

    fun clear() {
        mEditor!!.clear().commit()
    }

    fun remove(key: String) {
        mEditor!!.remove(key).commit()
    }
}