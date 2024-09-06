package com.example.mobydevozinshe.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    private var _language: MutableLiveData<String> = MutableLiveData()
    val language: MutableLiveData<String> = _language

    fun selectLanguage(language: String) {
        _language.postValue(language)
    }
}