package com.example.mobydevozinshe.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobydevozinshe.NavigationHostProvider
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.ActivityMainBinding
import java.util.Locale

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding!!.bottomNavbar.itemIconTintList = null
        binding!!.bottomNavbar.itemRippleColor = null
        binding!!.bottomNavbar.itemActiveIndicatorColor = null

        val navController = navHostFragment.navController
        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true).build()
        binding!!.bottomNavbar.setupWithNavController(navController)
        binding!!.bottomNavbar.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId, null, navOptions)
            true
        }
        systemLanguage()
        setDarkTheme(SharedProvider(this).getDarkTheme())
    }

    override fun hideSystemUI(boolean: Boolean) {
        if (boolean) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        } else {
            setDarkTheme(SharedProvider(this).getDarkTheme())
        }
    }

    override fun setNavigationVisibility(visible: Boolean) {
        if (visible) {
            binding!!.bottomNavbar.visibility = View.VISIBLE
        } else {
            binding!!.bottomNavbar.visibility = View.GONE
        }
    }

    private fun systemLanguage() {
        when (SharedProvider(this).getLanguage()) {
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

    override fun setDarkTheme(boolean: Boolean) {
        if (boolean) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.Grey900)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_VISIBLE or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        }
    }
}