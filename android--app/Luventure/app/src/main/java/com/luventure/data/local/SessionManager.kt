package com.luventure.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "luventure_session",
            Context.MODE_PRIVATE
        )

    fun saveToken(token: String) {
        prefs.edit()
            .putString("token", token)
            .apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun saveUserId(userId: String) {
        prefs.edit()
            .putString("userId", userId)
            .apply()
    }

    fun getUserId(): String? {
        return prefs.getString("userId", null)
    }

    fun clearToken() {
        prefs.edit()
            .remove("token")
            .apply()
    }

    fun clearSession() {
        prefs.edit()
            .remove("token")
            .remove("userId")
            .apply()
    }
}