package com.example.mobydevozinshe.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.data.model.UserProfileRequest
import com.example.mobydevozinshe.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser(SharedProvider(requireContext()).getToken())

        viewModel.userResponseItem.observe(viewLifecycleOwner) {
            binding.run {
                toolbar.title.text = "Nice"
                etUsername.setText(it.name)
                etEmail.setText(SharedProvider(requireContext()).getEmail())
                etPhone.setText(it.phoneNumber.toString())
                SharedProvider(requireContext()).saveBirthday(it.birthDate)
                etBirthday.setText(it.birthDate.toString())
                SharedProvider(requireContext()).saveID(it.id)
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.toolbar.title.text = "Error"
        }
        binding.run {
            toolbar.backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSave.setOnClickListener {
                SharedProvider(requireContext()).saveEmail(etEmail.text.toString())
                viewModel.updateUser(
                    SharedProvider(requireContext()).getToken(),
                    UserProfileRequest(
                        SharedProvider(requireContext()).getBirthday(),
                        SharedProvider(requireContext()).getID(),
                        SharedProvider(requireContext()).getLanguage(),
                        etUsername.text.toString(),
                        etPhone.text.toString()
                    )
                )
            }
        }
    }

}