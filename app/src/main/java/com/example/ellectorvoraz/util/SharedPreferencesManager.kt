package com.example.ellectorvoraz.util

import android.content.Context
import androidx.core.content.edit
import com.example.ellectorvoraz.data.model.Usuario

object SharedPreferencesManager {

    private const val PREFS_NAME = "ElLectorVorazPrefs"
    private const val KEY_AUTH_TOKEN = "AUTH_TOKEN"
    private const val KEY_USER_ID = "USER_ID"
    private const val KEY_USER_ROL_ID = "USER_ROL_ID"
    private const val KEY_USERNAME = "USERNAME"

    fun saveUserSession(context: Context, token: String, user: Usuario) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_AUTH_TOKEN, token)
            putInt(KEY_USER_ID, user.id)
            putInt(KEY_USER_ROL_ID, user.rolId)
            putString(KEY_USERNAME, user.username)
        }
    }

    fun getUser(context: Context): Usuario? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userId = prefs.getInt(KEY_USER_ID, -1)

        if (userId == -1) {
            return null
        }

        return Usuario(
            id = userId,
            rolId = prefs.getInt(KEY_USER_ROL_ID, 0),
            username = prefs.getString(KEY_USERNAME, "") ?: ""
        )
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