package com.example.mobydevozinshe.presentation.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentOnboardBinding
import com.example.mobydevozinshe.presentation.onboarding.screens.FirstScreen
import com.example.mobydevozinshe.presentation.onboarding.screens.SecondScreen
import com.example.mobydevozinshe.presentation.onboarding.screens.ThirdScreen
import com.example.mobydevozinshe.provideNavigationHost

class OnboardFragment : Fragment() {

    private lateinit var binding: FragmentOnboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        val pager2Callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (fragmentList.size - 1 == position){
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.INVISIBLE
                } else {
                    binding.btnFinish.visibility = View.INVISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(pager2Callback)
        binding.dotsIndicator.setViewPager2(binding.viewPager)


        binding.btnNext.setOnClickListener {
            binding.viewPager.currentItem++
        }

        binding.btnFinish.setOnClickListener {
            if (SharedProvider(requireContext()).isAuthorized())
                findNavController().navigate(R.id.action_onboardFragment_to_homeFragment)
            else
                findNavController().navigate(R.id.action_onboardFragment_to_authorizationFragment)
            onboardingFinished()
        }
    }

    private fun onboardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}