package com.relevanx.capstone_v1.stater_view


import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(PREF_LOGIN_STATUS, false)
    }

    fun setLoggedIn(value: Boolean) {
        preferences.edit().putBoolean(PREF_LOGIN_STATUS, value).apply()
    }

    fun setToken(value: String) {
        preferences.edit().putString(PREF_USER_TOKEN, value).apply()
    }

    fun getToken(): String {
        return preferences.getString(PREF_USER_TOKEN, "") ?: ""
    }

    fun logout() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "user_preference"
        private const val PREF_LOGIN_STATUS = "login_status"
        private const val PREF_USER_TOKEN = "user_token"
    }
}

