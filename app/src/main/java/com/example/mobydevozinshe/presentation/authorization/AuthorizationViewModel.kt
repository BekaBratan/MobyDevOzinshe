package com.example.mobydevozinshe.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.Auth
import com.example.mobydevozinshe.data.model.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationViewModel(): ViewModel() {
    private var _authorizationResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    val authorizationResponse: LiveData<AuthResponse> = _authorizationResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> = _errorResponse

    fun signUp(authorization: Auth) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signUp(authorization) }.fold(
                onSuccess = {
                    _authorizationResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun signIn(authorization: Auth) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signIn(authorization) }.fold(
                onSuccess = {
                    _authorizationResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }
}