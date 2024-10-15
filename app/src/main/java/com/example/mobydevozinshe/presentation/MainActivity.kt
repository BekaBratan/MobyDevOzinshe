package com.example.mobydevozinshe.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.marginBottom
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Visibility
import com.example.mobydevozinshe.NavigationHostProvider
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding!!.bottomNavbar.itemIconTintList = null
        val navController = navHostFragment.navController
        binding!!.bottomNavbar.setupWithNavController(navController)

        systemLanguage()
        systemDarkTheme()
    }

    override fun setNavigationVisability(visible: Boolean) {
        if (visible) {
            binding!!.bottomNavbar.visibility = View.VISIBLE
        } else {
            binding!!.bottomNavbar.visibility = View.GONE
        }
    }

    private fun systemLanguage() {
        when(SharedProvider(this).getLanguage()){
            "en" -> {
                val locale = Locale("en")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }
            "kk" -> {
                val locale = Locale("kk")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }
            "ru" -> {
                val locale = Locale("ru")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }
            else -> {
                val locale = Locale("kk")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }
        }
    }

    private fun systemDarkTheme() {
        if (SharedProvider(this).getDarkTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}