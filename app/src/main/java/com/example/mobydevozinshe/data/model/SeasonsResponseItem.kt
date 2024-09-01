package com.example.mobydevozinshe.data.model

data class SeasonsResponseItem(
    val id: Int,
    val movieId: Int,
    val number: Int,
    val videos: List<Video>
)