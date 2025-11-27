package com.example.ellectorvoraz.util

import android.content.Context
import androidx.core.content.edit

object SharedPreferencesManager {

    private const val PREFS_NAME = "ElLectorVorazPrefs"
    private const val KEY_AUTH_TOKEN = "AUTH_TOKEN"

    fun saveAuthToken(context: Context, token: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_AUTH_TOKEN, token)
        }
    }

    fun getAuthToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearSession(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { clear() }
    }
}