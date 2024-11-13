package com.example.mobydevozinshe.presentation.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.data.model.UserProfileRequest
import com.example.mobydevozinshe.databinding.FragmentEditProfileBinding
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.toString

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    var birthday = LocalDate.parse("2024-12-25", firstApiFormat)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser(SharedProvider(requireContext()).getToken())

        viewModel.userResponseItem.observe(viewLifecycleOwner) {
            binding.run {
                toolbar.title.text = getString(R.string.personal_info)
                etUsername.setText(it.name?:"")
                etEmail.setText(SharedProvider(requireContext()).getEmail())
                etPhone.setText(it.phoneNumber.toString()?:"")
                SharedProvider(requireContext()).saveBirthday(it.birthDate?: "")
                birthday = LocalDate.parse(it.birthDate?.toString(), firstApiFormat)
                etBirthday.setText(convertDateToString())
                SharedProvider(requireContext()).saveID(it.id)
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.toolbar.title.text = getString(R.string.errorConnection)
        }
        binding.run {
            toolbar.btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            etBirthday.setOnClickListener{
                openDatePicker()
            }

            btnSave.setOnClickListener {
                if (binding.etEmail.text.toString().isNotEmpty() && binding.etPhone.text.toString().isNotEmpty()
                    && binding.etBirthday.text.toString().isNotEmpty() && binding.etUsername.text.toString().isNotEmpty()) {
                    if (isValidEmail(binding.etEmail.text.toString())) {
                        SharedProvider(requireContext()).saveEmail(etEmail.text.toString())
                        viewModel.updateUser(
                            SharedProvider(requireContext()).getToken(),
                            UserProfileRequest(
                                birthday.toString(),
                                SharedProvider(requireContext()).getID(),
                                SharedProvider(requireContext()).getLanguage(),
                                binding.etUsername.text.toString(),
                                binding.etPhone.text.toString()
                            )
                        )
                    } else {
                        binding.tvError.text = getString(R.string.wrongFormat)
                        binding.tvError.visibility = View.VISIBLE
                    }
                } else {
                    binding.tvError.text = getString(R.string.fillAllFields)
                    binding.tvError.visibility = View.VISIBLE
                }
            }

            viewModel.userRequestItem.observe(viewLifecycleOwner) {
                findNavController().navigateUp()
            }

            viewModel.errorResponse.observe(viewLifecycleOwner) {
                binding.tvError.text = getString(R.string.errorConnection)
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), getString(R.string.errorConnection), Toast.LENGTH_SHORT).show()
            }

            etPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
                private var isUpdating = false
                private val phoneNumberPattern = "+# ### ###-##-##"

                override fun afterTextChanged(s: Editable?) {
                    if (isUpdating) {
                        isUpdating = false
                        return
                    }

                    val unformatted = s.toString().replace("\\D".toRegex(), "")
                    val formatted = formatPhoneNumber(unformatted)

                    isUpdating = true
                    s?.replace(0, s.length, formatted)
                }

                private fun formatPhoneNumber(number: String): String {
                    var formatted = ""
                    var i = 0
                    var prev = ""

                    phoneNumberPattern.forEach { char ->
                        if (char == '#' && i < number.length) {
                            if (prev.isNotEmpty()) {
                                formatted += prev
                                prev = ""
                            }
                            formatted += number[i]
                            i++
                        } else if (char != '#') {
                            prev = char.toString()
                        }
                    }

                    return formatted
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun openDatePicker() {
        val dialog = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val newMonth = month+1
                val strMonth = if (newMonth<10) "0$newMonth" else newMonth.toString()
                val strDay = if (day<10) "0$day" else day.toString()
                birthday = LocalDate.parse("$year-$strMonth-$strDay", firstApiFormat)
                binding.etBirthday.setText(convertDateToString())
            },
            birthday.year, birthday.monthValue-1, birthday.dayOfMonth
        )

        dialog.show()
    }

    @SuppressLint("NewApi")
    fun convertDateToString(): String {
        return "${birthday.dayOfMonth} ${getString(resources.getIdentifier(birthday.month.toString().lowercase(), "string", requireContext().packageName))} ${birthday.year}"
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}