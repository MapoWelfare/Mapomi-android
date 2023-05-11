package io.mapomi.android.system

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context : Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences(PREFERENCE,0)
    private var editor : SharedPreferences.Editor = prefs.edit()

    fun getString(key: String, defValue : String?) : String? {
        return prefs.getString(key,defValue)
    }

    fun setString(key: String, str : String?) {
        editor.putString(key,str).apply()
    }

    fun getInt(key: String, int: Int): Int {
        return prefs.getInt(key, int)
    }

    fun setInt(key: String, int: Int) {
        editor.putInt(key, int).commit()
    }

    fun getBoolean(key: String, boolean: Boolean): Boolean {
        return prefs.getBoolean(key, boolean)
    }

    fun setBoolean(key: String, boolean: Boolean) {
        editor.putBoolean(key, boolean).apply()
    }

    fun clear() {
        editor.clear().apply()
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    companion object {
        const val PREFERENCE = "PREFERENCE"
    }
}