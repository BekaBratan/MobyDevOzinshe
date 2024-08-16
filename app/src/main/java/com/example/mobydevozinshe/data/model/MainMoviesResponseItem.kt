package com.example.mobydevozinshe.data.model

data class MainMoviesResponseItem(
    val fileId: Int,
    val id: Int,
    val link: String,
    val movie: MoviesResponseItem,
    val sortOrder: Int
)