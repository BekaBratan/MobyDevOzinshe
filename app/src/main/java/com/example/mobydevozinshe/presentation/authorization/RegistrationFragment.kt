package com.example.mobydevozinshe.presentation.authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentRegistrationBinding
import com.example.mobydevozinshe.data.model.AuthRequest
import com.example.mobydevozinshe.provideNavigationHost

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authorizationResponse.observe(viewLifecycleOwner) {
            Log.d("BBBB", "Success signIn \n $it")
            SharedProvider(requireContext()).saveUser(it)
            binding.tvError.visibility = View.GONE
            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.tvError.text = getString(R.string.errorSignIn)
            binding.tvError.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvLink.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidEmail(binding.etEmail.text.toString()) && binding.etEmail.text.toString().isNotEmpty()) {
                    binding.tvWrongFormatEmail.visibility = View.VISIBLE
                    binding.btnFinish.isClickable = false
                    binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.sh_input_error)
                }
            }
        }

        binding.etEmail.addTextChangedListener {
            if (isValidEmail(binding.etEmail.text.toString()) || binding.etEmail.text.toString().isEmpty()) {
                binding.tvWrongFormatEmail.visibility = View.GONE
                binding.btnFinish.isClickable = true
                binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.sh_input)
            }
        }

        binding.btnFinish.setOnClickListener {
            if (binding.etEmail.text.toString().isNotEmpty() && binding.etPassword1.text.toString().isNotEmpty()){
                if (isValidEmail(binding.etEmail.text.toString())) {
                    if (binding.etPassword1.text.toString() == binding.etPassword2.text.toString()) {
                        val authRequest = AuthRequest(binding.etEmail.text.toString(), binding.etPassword1.text.toString())
                        viewModel.signUp(authRequest)
                    } else {
                        binding.tvError.text = getString(R.string.passwordsNotSame)
                        binding.tvError.visibility = View.VISIBLE
                    }
                } else {
                    binding.tvError.text = getString(R.string.wrongFormat)
                    binding.tvError.visibility = View.VISIBLE
                }
            } else {
                binding.tvError.text = getString(R.string.fillAllFields)
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}