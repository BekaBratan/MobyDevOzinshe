package com.example.mobydevozinshe.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Visibility
import com.example.mobydevozinshe.NavigationHostProvider
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.databinding.ActivityMainBinding

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
    }

    override fun setNavigationVisability(visible: Boolean) {
        if (visible) {
            binding!!.bottomNavbar.visibility = View.VISIBLE
        } else {
            binding!!.bottomNavbar.visibility = View.GONE
        }
    }
}