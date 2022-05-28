package com.baig.taskgrocery.utils

import android.content.Context

class PrefUtil {

    private val context: Context? = null

    val PREFS_NAME = "my_prefs"


    fun setInt(key: String?, value: Int) {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?, defValue: Int): Int {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getInt(key, defValue)
    }

    fun setString(key: String?, value: String?) {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getString(key, "null")
    }

    fun setBool(key: String?, value: Boolean) {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBool(key: String?): Boolean {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getBoolean(key, false)
    }

    fun getBool(key: String?, defaultValue: Boolean): Boolean {
        val prefs = context!!.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getBoolean(key, defaultValue)
    }

}