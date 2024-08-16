package com.example.mobydevozinshe.data.repository

import com.example.mobydevozinshe.data.api.ServiceBuilder
import com.example.mobydevozinshe.data.model.Auth
import com.example.mobydevozinshe.data.model.Movies
import com.example.mobydevozinshe.data.model.AuthResponse
import com.example.mobydevozinshe.data.model.MainMovies

class Repository {
    suspend fun signUp(authorization: Auth): AuthResponse {
        return ServiceBuilder.api.signUp(authorization)
    }

    suspend fun signIn(authorization: Auth): AuthResponse {
        return ServiceBuilder.api.signIn(authorization)
    }

    suspend fun getMovies(token: String): Movies {
        return ServiceBuilder.api.getMoviesList(token)
    }

    suspend fun getMainMovies(token: String): MainMovies {
        return ServiceBuilder.api.getMainMoviesList(token)
    }
}