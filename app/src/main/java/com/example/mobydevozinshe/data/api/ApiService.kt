package com.example.mobydevozinshe.data.api

import com.example.mobydevozinshe.data.model.Auth
import com.example.mobydevozinshe.data.model.Movies
import com.example.mobydevozinshe.data.model.AuthResponse
import com.example.mobydevozinshe.data.model.MainMovies
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/V1/signup")
    suspend fun signUp(
        @Body authorization: Auth
    ): AuthResponse

    @POST("/auth/V1/signin")
    suspend fun signIn(
        @Body authorization: Auth
    ): AuthResponse

    @GET("/core/V1/movies")
    suspend fun getMoviesList(
        @Header("Authorization") token: String
    ): Movies

    @GET("/core/V1/movies/main")
    suspend fun getMainMoviesList(
        @Header("Authorization") token: String
    ): MainMovies
}