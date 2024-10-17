package com.example.mobydevozinshe.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.AuthResponse
import com.example.mobydevozinshe.data.model.ChangePasswordRequest
import kotlinx.coroutines.launch

class ChangePasswordViewModel: ViewModel() {
    private var _changePasswordResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    val changePasswordResponse: MutableLiveData<AuthResponse> = _changePasswordResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun changePassword(token: String, newPassword: ChangePasswordRequest) {
        viewModelScope.launch {
            runCatching { ServiceBuilder.api.changePassword(token, newPassword) }.fold(
                onSuccess = { _changePasswordResponse.postValue(it) },
                onFailure = { _errorResponse.postValue(it.message) }
            )
        }
    }
}