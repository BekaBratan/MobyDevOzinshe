package com.example.mobydevozinshe.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.provideNavigationHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisability(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(3000)
            if (isOnboardingFinished())
                if (isAuthorized())
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                else
                    findNavController().navigate(R.id.action_splashScreenFragment_to_authorizationFragment)
            else
                findNavController().navigate(R.id.action_splashScreenFragment_to_onboardFragment)
        }
    }

    private fun isOnboardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun isAuthorized(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isAuthorized", false)
    }
}