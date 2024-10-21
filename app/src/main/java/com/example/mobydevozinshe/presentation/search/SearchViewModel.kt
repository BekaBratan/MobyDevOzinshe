package com.example.mobydevozinshe.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.CategoriesResponse
import com.example.mobydevozinshe.data.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(): ViewModel() {
    private var _categoriesResponse: MutableLiveData<CategoriesResponse> = MutableLiveData()
    val categoriesResponse: LiveData<CategoriesResponse> = _categoriesResponse

    private var _searchResponse: MutableLiveData<MoviesResponse> = MutableLiveData()
    val searchResponse: LiveData<MoviesResponse> = _searchResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> = _errorResponse

    fun getCategories(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getCategories(token) }.fold(
                onSuccess = {
                    _categoriesResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }

    fun searchMovies(token: String, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.searchMovies(token, query) }.fold(
                onSuccess = {
                    _searchResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }
}