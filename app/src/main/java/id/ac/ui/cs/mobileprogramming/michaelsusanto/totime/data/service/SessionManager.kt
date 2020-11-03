package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service

import android.content.Context
import android.content.SharedPreferences
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    /**
     * Function to save data
     */
    fun saveData(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Function to fetch data
     */
    fun fetchData(key: String): String? {
        return prefs.getString(key, null)
    }
}