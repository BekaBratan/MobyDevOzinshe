package com.example.mobydevozinshe.presentation.authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentAuthorizationBinding
import com.example.mobydevozinshe.data.model.Auth

class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authorizationResponse.observe(viewLifecycleOwner) {
            Log.d("BBBB", "Success signIn \n $it")
            SharedProvider(requireContext()).saveUser(it)
            findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
            binding.tvError.visibility = View.GONE
            findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            Log.e("BBBB", "Fail signIn $it")
            binding.tvError.visibility = View.VISIBLE
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvLink.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (isValidEmail(binding.etEmail.text.toString()) || binding.etEmail.text.toString().isEmpty()) {
                    binding.tvWrongFormatEmail.visibility = View.GONE
                    binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(),
                        R.drawable.sh_input
                    )
                } else {
                    binding.tvWrongFormatEmail.visibility = View.VISIBLE
                    binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(),
                        R.drawable.sh_input_error
                    )
                }
            }
        }

        binding.btnFinish.setOnClickListener {
            if (isValidEmail(binding.etEmail.text.toString()) && binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString()
                    .isNotEmpty()){
                val auth = Auth(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                viewModel.signIn(auth)
            } else {
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}