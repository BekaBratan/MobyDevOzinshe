package com.example.mobydevozinshe.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.UserProfileRequest
import com.example.mobydevozinshe.data.model.UserProfileResponse
import kotlinx.coroutines.launch

class EditProfileViewModel: ViewModel() {
    private var _userResponseItem: MutableLiveData<UserProfileResponse> = MutableLiveData()
    val userResponseItem: MutableLiveData<UserProfileResponse> = _userResponseItem

    private var _userRequestItem: MutableLiveData<UserProfileResponse> = MutableLiveData()
    val userRequestItem: MutableLiveData<UserProfileResponse> = _userRequestItem

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun getUser(token: String) {
        viewModelScope.launch {
            runCatching { ServiceBuilder.api.getUserProfile(token) }
                .onSuccess { _userResponseItem.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }

    fun updateUser(token: String, userRequestItem: UserProfileRequest) {
        viewModelScope.launch {
            runCatching { ServiceBuilder.api.updateUserProfile(token, userRequestItem) }
                .onSuccess { _userRequestItem.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }
}