package com.example.mobydevozinshe.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.MovieIdModel
import com.example.mobydevozinshe.data.model.MoviesResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private var _movieResponseItem: MutableLiveData<MoviesResponseItem> = MutableLiveData()
    val movieResponseItem: MutableLiveData<MoviesResponseItem> = _movieResponseItem

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    private var _favouriteState: MutableLiveData<Boolean> = MutableLiveData()
    val favouriteState: MutableLiveData<Boolean> = _favouriteState

    fun getMovie(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getMovie(token, id) }
                .onSuccess { _movieResponseItem.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }

    fun addFavourite(token: String, id: MovieIdModel) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.addFavourite(token, id) }
                .onSuccess { _favouriteState.postValue(true) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }

    fun deleteFavourite(token: String, id: MovieIdModel) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.deleteFavourite(token, id) }
                .onSuccess { _favouriteState.postValue(false) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }
}