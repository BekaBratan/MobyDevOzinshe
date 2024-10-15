package com.example.mobydevozinshe.presentation.profile

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentProfileBinding
import com.example.mobydevozinshe.provideNavigationHost
import java.util.Locale

class ProfileFragment : Fragment(), OnLanguageSelectedListener {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisability(true)
        }
        binding.switchDarkTheme.isChecked = SharedProvider(requireContext()).getDarkTheme()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recreate fragment to avoid blinking while changing language
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            transaction.setReorderingAllowed(false)
        }
        transaction.detach(this).attach(this).commit()

        viewModel.language.observe(viewLifecycleOwner) {
            binding.tvLanguage.text = it
        }

        binding.llLanguage.setOnClickListener {
            val bottomsheet = SelectedLanguageBottomsheet()
            bottomsheet.setOnLanguageSelectedListener(this)
            bottomsheet.show(childFragmentManager, bottomsheet.tag)
        }

        binding.run {
            tvEmail.text = SharedProvider(requireContext()).getEmail()

            switchDarkTheme.setOnCheckedChangeListener { _, isCheked ->
                SharedProvider(requireContext()).saveDarkTheme(isCheked)
                AppCompatDelegate.setDefaultNightMode(
                    if (isCheked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
                requireActivity().recreate()
            }

            toolbar.backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.title.text = getString(R.string.profile)

            toolbar.exitButton.setOnClickListener {
                val bottomsheet = ExitBottomsheet()
                bottomsheet.show(childFragmentManager, bottomsheet.tag)
            }

            llPersonalInfo.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
        }
    }

    override fun onLanguageSelected(language: String) {
        viewModel.selectLanguage(language)
    }
}