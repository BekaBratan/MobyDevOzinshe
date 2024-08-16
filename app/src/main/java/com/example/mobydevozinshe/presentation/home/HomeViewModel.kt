package com.example.mobydevozinshe.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.MainCategoryMoviesResponse
import com.example.mobydevozinshe.data.model.MainMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private var _mainMoviesResponse: MutableLiveData<MainMoviesResponse> = MutableLiveData()
    val mainMoviesResponse: LiveData<MainMoviesResponse> = _mainMoviesResponse

    private var _mainCategoryMoviesResponse: MutableLiveData<MainCategoryMoviesResponse> = MutableLiveData()
    val mainCategoryMoviesResponse: LiveData<MainCategoryMoviesResponse> = _mainCategoryMoviesResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> = _errorResponse

    fun getMainMovies(token: String) {
         viewModelScope.launch(Dispatchers.IO) {
             runCatching { ServiceBuilder.api.getMainMoviesList(token) }.fold(
                 onSuccess = {
                     _mainMoviesResponse.postValue(it)
                 },
                 onFailure = {
                     _errorResponse.postValue(it.message)
                 }
             )
         }
    }

    fun getMainCategoryMovies(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getMainCategoryMoviesList(token) }.fold(
                onSuccess = {
                    _mainCategoryMoviesResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }
}