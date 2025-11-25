package com.example.ellectorvoraz.util

import android.content.Context
import androidx.core.content.edit

object SharedPreferencesManager {

    private const val PREFS_NAME = "ElLectorVorazPrefs"
    private const val KEY_AUTH_TOKEN = "AUTH_TOKEN"
    private const val KEY_USER_ID = "USER_ID"

    fun saveSession(context: Context, token: String, userId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_AUTH_TOKEN, token).putInt(KEY_USER_ID, userId) }
    }

    fun getAuthToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun getUserId(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_USER_ID, -1)
    }

    fun clearSession(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { clear() }
    }

}