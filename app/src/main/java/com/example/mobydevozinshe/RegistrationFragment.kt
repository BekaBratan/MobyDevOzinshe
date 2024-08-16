package com.example.mobydevozinshe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.databinding.FragmentRegistrationBinding
import com.example.mobydevozinshe.model.Auth
import com.example.mobydevozinshe.model.AuthResponse
import com.example.mobydevozinshe.repository.Repository
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvLink.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (isValidEmail(binding.etEmail.text.toString()) || binding.etEmail.text.toString().isEmpty()) {
                    binding.tvWrongFormatEmail.visibility = View.GONE
                    binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.sh_input)
                } else {
                    binding.tvWrongFormatEmail.visibility = View.VISIBLE
                    binding.etEmail.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.sh_input_error)
                }
            }
        }

        binding.btnFinish.setOnClickListener {
            if (isValidEmail(binding.etEmail.text.toString()) && binding.etEmail.text.toString().isNotEmpty() && binding.etPassword1.text.toString()
                    .isNotEmpty() && binding.etPassword1.text.toString() == binding.etPassword2.text.toString()){
                val auth = Auth(binding.etEmail.text.toString(), binding.etPassword1.text.toString())
                var user: AuthResponse
                lifecycleScope.launch {
                    runCatching { repository.signUp(auth) }
                        .fold(
                            onSuccess = {
                                Log.d("BBBB", "Success signUp \n $it")
                                user = AuthResponse(it.accessToken, it.email, it.id, it.roles, it.tokenType, it.username)
                                binding.tvError.visibility = View.GONE
                                repository.signIn(auth)
                                saveUser(user)
                                findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                            },
                            onFailure = {
                                Log.e("BBBB", "Fail signIn $it")
                                binding.tvError.visibility = View.VISIBLE
                            }
                        )
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUser(user: AuthResponse) {
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isAuthorized", true)
        editor.putString("AccessToken", user.accessToken)
        editor.putString("Email", user.email)
        editor.putStringSet("Roles", user.roles.toSet())
        editor.putInt("ID", user.id)
        editor.putString("TokenType", user.tokenType)
        editor.putString("Username", user.username)
        editor.apply()
    }
}