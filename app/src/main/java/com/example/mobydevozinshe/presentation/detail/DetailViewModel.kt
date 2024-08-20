package com.example.mobydevozinshe.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.MoviesResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private var _movieResponseItem: MutableLiveData<MoviesResponseItem> = MutableLiveData()
    val movieResponseItem: MutableLiveData<MoviesResponseItem> = _movieResponseItem

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun getMovie(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getMovie(token, id) }
                .onSuccess { _movieResponseItem.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }
}