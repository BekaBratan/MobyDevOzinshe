package com.example.mobydevozinshe.presentation.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.MoviesPageResponse
import com.example.mobydevozinshe.data.model.SimilarMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(): ViewModel() {
    private var _moviesPageResponse: MutableLiveData<MoviesPageResponse> =
        MutableLiveData()
    val moviesPageResponse: MutableLiveData<MoviesPageResponse> = _moviesPageResponse

    private var _similarMoviesResponse: MutableLiveData<SimilarMoviesResponse> = MutableLiveData()
    val similarMoviesResponse: MutableLiveData<SimilarMoviesResponse> = _similarMoviesResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun getGenrePage(token: String, genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getGenrePage(token = token, genreId = genreId) }.fold(
                onSuccess = {
                    _moviesPageResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun getAgeCategoryPage(token: String, ageCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getAgeCategoryPage(token = token, categoryAgeId = ageCategoryId) }.fold(
                onSuccess = {
                    _moviesPageResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun getCategoryPage(token: String, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getCategoryPage(token = token, categoryId = categoryId) }.fold(
                onSuccess = {
                    _moviesPageResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun getSimilarMovies(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getSimilarMoviesList(token, id) }
                .onSuccess { _similarMoviesResponse.postValue(it) }
                .onFailure { _errorResponse.postValue(it.message) }
        }
    }
}