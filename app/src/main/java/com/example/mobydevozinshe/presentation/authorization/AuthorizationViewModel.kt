package com.example.mobydevozinshe.presentation.authorization

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.AuthRequest
import com.example.mobydevozinshe.data.model.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationViewModel(private val context: Context): ViewModel() {
    private var _authorizationResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    val authorizationResponse: LiveData<AuthResponse> = _authorizationResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> = _errorResponse

    fun signUp(authorization: AuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signUp(authorization) }
                .onSuccess {
                    _authorizationResponse.postValue(it)
                }
                .onFailure {
                    if (it.message!!.contains("HTTP 401")) {
                        _errorResponse.postValue(context.getString(R.string.errorSignIn))
                    } else {
                        _errorResponse.postValue(context.getString(R.string.errorConnection))
                    }
                }
        }
    }

    fun signIn(authorization: AuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signIn(authorization) }.fold(
                onSuccess = {
                    _authorizationResponse.postValue(it)
                },
                onFailure = {
                    if (it.message!!.contains("HTTP 401")) {
                        _errorResponse.postValue(context.getString(R.string.errorSignIn))
                    } else {
                        _errorResponse.postValue(context.getString(R.string.errorConnection))
                    }
                }
            )
        }
    }
}