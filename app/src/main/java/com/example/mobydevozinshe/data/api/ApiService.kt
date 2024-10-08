package com.example.mobydevozinshe.data.api

import com.example.mobydevozinshe.data.model.Auth
import com.example.mobydevozinshe.data.model.MoviesResponse
import com.example.mobydevozinshe.data.model.AuthResponse
import com.example.mobydevozinshe.data.model.MainCategoryMoviesResponse
import com.example.mobydevozinshe.data.model.MainMoviesResponse
import com.example.mobydevozinshe.data.model.MovieIdModel
import com.example.mobydevozinshe.data.model.MoviesResponseItem
import com.example.mobydevozinshe.data.model.SeasonsResponse
import com.example.mobydevozinshe.data.model.UserProfileRequest
import com.example.mobydevozinshe.data.model.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("/core/V1/seasons/{id}")
    suspend fun getSeasons(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): SeasonsResponse

    @POST("/core/V1/favorite")
    suspend fun addFavourite(
        @Header("Authorization") token: String,
        @Body movieBody: MovieIdModel
    ): MovieIdModel

    @HTTP(method = "DELETE", path = "/core/V1/favorite/", hasBody = true)
    suspend fun deleteFavourite(
        @Header("Authorization") token: String,
        @Body movieBody: MovieIdModel
    ): Unit

    @GET("/core/V1/user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse

    @HTTP(method = "PUT", path = "/core/V1/user/profile/", hasBody = true)
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userProfile: UserProfileRequest
    ): UserProfileResponse

}