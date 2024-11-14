package com.example.mobydevozinshe

interface NavigationHostProvider {
    fun setNavigationVisibility(visible: Boolean)
    fun hideSystemUI(boolean: Boolean)
    fun setDarkTheme(boolean: Boolean)
}
