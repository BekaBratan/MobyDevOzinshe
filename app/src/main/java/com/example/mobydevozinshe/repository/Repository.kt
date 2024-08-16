package com.example.mobydevozinshe.repository

import com.example.mobydevozinshe.api.RetrofitInstance
import com.example.mobydevozinshe.model.Auth
import com.example.mobydevozinshe.model.Movies
import com.example.mobydevozinshe.model.AuthResponse
import com.example.mobydevozinshe.model.MainMovies

class Repository {
    suspend fun signUp(authorization: Auth): AuthResponse {
        return RetrofitInstance.api.signUp(authorization)
    }

    suspend fun signIn(authorization: Auth): AuthResponse {
        return RetrofitInstance.api.signIn(authorization)
    }

    suspend fun getMovies(token: String): Movies {
        return RetrofitInstance.api.getMoviesList(token)
    }

    suspend fun getMainMovies(token: String): MainMovies {
        return RetrofitInstance.api.getMainMoviesList(token)
    }
}