package com.example.mobydevozinshe.data.model

data class MainCategoryMoviesResponseItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<MoviesResponseItem>
)