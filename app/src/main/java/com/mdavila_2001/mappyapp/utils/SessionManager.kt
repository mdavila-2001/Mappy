package com.mdavila_2001.mappyapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    companion object {
        private const val USERNAME = "username"
    }

    fun saveUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUserName(): String? {
        return prefs.getString(USERNAME, null)
    }

    fun isLoggedIn(): Boolean {
        return getUserName() != null
    }

    fun logout() {
        val editor = prefs.edit()
        editor.remove(USERNAME)
        editor.apply()
    }
}