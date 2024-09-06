package com.example.mobydevozinshe.presentation.profile

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.BottomsheetSelectLanguageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class SelectedLanguageBottomsheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetSelectLanguageBinding
    private var languageSelectedListener: OnLanguageSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetSelectLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val defaultLanguage: String = SharedProvider(requireContext()).getLanguage()
        when(defaultLanguage){
            "en" -> {
                selectedIcon(true, false, false)
            }
            "kk" -> {
                selectedIcon(false, true, false)
            }
            "ru" -> {
                selectedIcon(false, false, true)
            }
        }

        binding.run {
            llEnglish.setOnClickListener {
                selectedIcon(true, false, false)
                changeLanguage("English")
            }
            llKazakh.setOnClickListener {
                selectedIcon(false, true, false)
                changeLanguage("Қазақша")
            }
            llRussian.setOnClickListener {
                selectedIcon(false, false, true)
                changeLanguage("Руский")
            }
        }
    }

    private fun changeLanguage(language: String) {
        when(language){
            "English" -> {
                systemLanguageChange("en")
                languageSelectedListener?.onLanguageSelected("English")
            }
            "Қазақша" -> {
                systemLanguageChange("kk")
                languageSelectedListener?.onLanguageSelected("Қазақша")
            }
            "Руский" -> {
                systemLanguageChange("ru")
                languageSelectedListener?.onLanguageSelected("Руский")
            }
        }
    }

    private fun systemLanguageChange(codeLanguage: String) {
        SharedProvider(requireContext()).saveLanguage(codeLanguage)

        // Change application interface language
        val locale = Locale(codeLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        // This code navigates to the profile Fragment while clearing the navigation stack to that fragment
        // This approach is useful to prevent fragments from accumulating on the stack and to avoid reopening the same fragment
        findNavController().navigate(R.id.profileFragment, arguments, NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build())
    }

    private fun selectedIcon(iconEnglish: Boolean, iconKazakh: Boolean, iconRussian: Boolean) {
        binding.ibCheckEnglish.visibility = if (iconEnglish) View.VISIBLE else View.GONE
        binding.ibCheckKazakh.visibility = if (iconKazakh) View.VISIBLE else View.GONE
        binding.ibCheckRussian.visibility = if (iconRussian) View.VISIBLE else View.GONE
    }

    fun setOnLanguageSelectedListener(listener: OnLanguageSelectedListener) {
        languageSelectedListener = listener
    }
}