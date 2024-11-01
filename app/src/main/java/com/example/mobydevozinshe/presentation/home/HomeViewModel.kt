package com.example.mobydevozinshe.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.CategoryAgesResponse
import com.example.mobydevozinshe.data.model.GenreResponse
import com.example.mobydevozinshe.data.model.MainCategoryMoviesResponse
import com.example.mobydevozinshe.data.model.MainMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    val scrollPosition = MutableLiveData<Int>()

    private var _mainMoviesResponse: MutableLiveData<MainMoviesResponse> = MutableLiveData()
    val mainMoviesResponse: LiveData<MainMoviesResponse> = _mainMoviesResponse

    private var _mainCategoryMoviesResponse: MutableLiveData<MainCategoryMoviesResponse> = MutableLiveData()
    val mainCategoryMoviesResponse: LiveData<MainCategoryMoviesResponse> = _mainCategoryMoviesResponse

    private var _genresResponse: MutableLiveData<GenreResponse> = MutableLiveData()
    val genresResponse: LiveData<GenreResponse> = _genresResponse

    private var _categoryAgesResponse: MutableLiveData<CategoryAgesResponse> = MutableLiveData()
    val categoryAgesResponse: LiveData<CategoryAgesResponse> = _categoryAgesResponse

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

    fun getGenres(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getGenres(token) }.fold(
                onSuccess = {
                    _genresResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun getCategoryAges(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getCategoryAges(token) }.fold(
                onSuccess = {
                    _categoryAgesResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }
}