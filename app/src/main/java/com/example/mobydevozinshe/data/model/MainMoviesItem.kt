package com.example.mobydevozinshe.data.model

data class MainMoviesItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<MoviesItem>
)