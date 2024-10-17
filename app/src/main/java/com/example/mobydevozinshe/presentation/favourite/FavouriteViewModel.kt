package com.example.mobydevozinshe.presentation.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.FavouriteMoviesResponse
import com.example.mobydevozinshe.data.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(): ViewModel() {
    private var _favouriteMoviesResponse: MutableLiveData<FavouriteMoviesResponse> = MutableLiveData()
    val favouriteMoviesResponse: MutableLiveData<FavouriteMoviesResponse> = _favouriteMoviesResponse

    private var _errorResponse: MutableLiveData<String> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = _errorResponse

    fun getFavouriteMovies(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.getFavouriteMoviesList(token) }.fold(
                onSuccess = {
                    _favouriteMoviesResponse.postValue(it)
                },
                onFailure = {
                    _errorResponse.postValue(it.message)
                }
            )
        }
    }
}