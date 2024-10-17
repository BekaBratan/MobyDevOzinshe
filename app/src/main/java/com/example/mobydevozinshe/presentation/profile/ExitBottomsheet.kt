package com.example.mobydevozinshe.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.BottomsheetExitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExitBottomsheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetExitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.setOnClickListener {
            SharedProvider(requireContext()).clearShared()
            findNavController().navigate(R.id.action_profileFragment_to_authorizationFragment)
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }
}