package com.example.mobydevozinshe.data.model

data class MoviesItem(
    val categories: List<Category>,
    val categoryAges: List<CategoryAge>,
    val createdDate: String,
    val description: String,
    val director: String,
    val favorite: Boolean,
    val genres: List<Genre>,
    val id: Int,
    val keyWords: String,
    val lastModifiedDate: String,
    val movieType: String,
    val name: String,
    val poster: Poster,
    val producer: String,
    val screenshots: List<Screenshot>,
    val seasonCount: Int,
    val seriesCount: Int,
    val timing: Int,
    val trend: Boolean,
    val video: Video,
    val watchCount: Int,
    val year: Int
)