package com.example.mobydevozinshe.model

data class MainMoviesItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<MoviesItem>
)