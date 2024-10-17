package com.example.mobydevozinshe.data

import android.content.Context
import android.content.SharedPreferences
import com.example.mobydevozinshe.data.model.AuthResponse

class SharedProvider(private val context: Context) {
    private val sharedToken = "AccessToken"
    private val tokenType = "TokenType"
    private val isAuthorized = "isAuthorized"

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    private val systemPreferences: SharedPreferences
        get() = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    private fun saveToken(token: String) {
        preferences.edit().putString(sharedToken, token).apply()
    }

    fun getToken():String {
        return "${preferences.getString(tokenType, "without_token_type")} ${preferences.getString(sharedToken, "without_token")}"
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
        editor.putString(tokenType, user.tokenType)
        editor.putString("Username", user.username)
        editor.apply()
        saveToken(user.accessToken)
    }

    fun isAuthorized(): Boolean {
        return preferences.getBoolean(isAuthorized, false)
    }

    fun saveLanguage(language: String) {
        systemPreferences.edit().putString("Language", language).apply()
    }

    fun getLanguage(): String {
        return systemPreferences.getString("Language", "kk").toString()
    }

    fun saveDarkTheme(isDark: Boolean) {
        systemPreferences.edit().putBoolean("DarkTheme", isDark).apply()
    }

    fun getDarkTheme(): Boolean {
        return systemPreferences.getBoolean("DarkTheme", false)
    }

    fun getEmail(): String {
        return preferences.getString(("Email"), "no email").toString()
    }

    fun saveEmail(email: String) {
        preferences.edit().putString("Email", email).apply()
    }

    fun getID(): Int {
        return preferences.getInt(("ID"), 0)
    }

    fun saveID(id: Int) {
        preferences.edit().putInt("ID", id).apply()
    }

    fun saveBirthday(birthday: Any) {
        preferences.edit().putString("Birthday", birthday.toString()).apply()
    }
}