package com.example.mobydevozinshe.data

import android.content.Context
import android.content.SharedPreferences
import com.example.mobydevozinshe.data.model.AuthResponse

class SharedProvider(private val context: Context) {
    private val shared_token = "AccessToken"
    private val token_type = "TokenType"
    private val is_authorized = "isAuthorized"

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString(shared_token, token).apply()
    }

    fun getToken():String {
        return "${preferences.getString(token_type, "without_token_type")} ${preferences.getString(shared_token, "without_token")}"
    }

    fun clearShared() {
        preferences.edit().clear().apply()
    }

    fun saveUser(user: AuthResponse) {
        val sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.putBoolean("isAuthorized", true)
        editor.putString("Email", user.email)
        editor.putStringSet("Roles", user.roles.toSet())
        editor.putInt("ID", user.id)
        editor.putString(token_type, user.tokenType)
        editor.putString("Username", user.username)
        editor.apply()
        saveToken(user.accessToken)
    }

    fun isAuthorized(): Boolean {
        return preferences.getBoolean(is_authorized, false)
    }

    fun saveLanguage(language: String) {
        preferences.edit().putString("Language", language).apply()
    }

    fun getLanguage(): String {
        return preferences.getString("Language", "kk").toString()
    }

    fun saveDarkTheme(isDark: Boolean) {
        preferences.edit().putBoolean("DarkTheme", isDark).apply()
    }

    fun getDarkTheme(): Boolean {
        return preferences.getBoolean("DarkTheme", false)
    }
}