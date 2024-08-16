package com.example.mobydevozinshe.data.model

data class AuthResponse(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)