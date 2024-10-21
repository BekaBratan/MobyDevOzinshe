package com.example.mobydevozinshe.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.data.model.ChangePasswordRequest
import com.example.mobydevozinshe.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbar.title.text = getString(R.string.change_password)

        binding.btnSave.setOnClickListener {
            if (binding.etPassword1.text.toString() != binding.etPassword2.text.toString()) {
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
                viewModel.changePassword(
                    SharedProvider(requireContext()).getToken(),
                    ChangePasswordRequest(
                        binding.etPassword1.text.toString()
                    )
                )
            }
        }

        viewModel.changePasswordResponse.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.tvError.text = it
            binding.tvError.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }
    }

}