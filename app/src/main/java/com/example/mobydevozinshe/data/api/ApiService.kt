package com.example.mobydevozinshe.data.api

import com.example.mobydevozinshe.data.model.Auth
import com.example.mobydevozinshe.data.model.MoviesResponse
import com.example.mobydevozinshe.data.model.AuthResponse
import com.example.mobydevozinshe.data.model.MainCategoryMoviesResponse
import com.example.mobydevozinshe.data.model.MainMoviesResponse
import com.example.mobydevozinshe.data.model.MoviesResponseItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/auth/V1/signup")
    suspend fun signUp(
        @Body authorization: Auth
    ): AuthResponse

    @POST("/auth/V1/signin")
    suspend fun signIn(
        @Body authorization: Auth
    ): AuthResponse

    @GET("/core/V1/movies/main")
    suspend fun getMainCategoryMoviesList(
        @Header("Authorization") token: String
    ): MainCategoryMoviesResponse

    @GET("/core/V1/movies_main")
    suspend fun getMainMoviesList(
        @Header("Authorization") token: String
    ): MainMoviesResponse

    @GET("core/V1/movies/{id}")
    suspend fun getMovie(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): MoviesResponseItem
}