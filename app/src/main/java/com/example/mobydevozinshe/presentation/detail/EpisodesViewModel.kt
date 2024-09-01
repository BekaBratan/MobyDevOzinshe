package com.example.mobydevozinshe.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.SeasonsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodesViewModel: ViewModel() {
    private var _seasonsResponseItem: MutableLiveData<SeasonsResponse> = MutableLiveData()
    val seasonsResponseItem: MutableLiveData<SeasonsResponse> = _seasonsResponseItem

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun getSeasons(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getSeasons(token, id) }
                .onSuccess { _seasonsResponseItem.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }
}