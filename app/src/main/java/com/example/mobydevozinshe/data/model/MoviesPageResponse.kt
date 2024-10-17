package com.example.mobydevozinshe.data.model

data class MoviesPageResponse(
    val content: List<MoviesResponseItem>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
)