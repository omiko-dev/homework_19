package com.example.homework_19.data.dto

import com.squareup.moshi.Json

data class UsersDto(
    val avatar: String,
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    val id: Int,
    @Json(name = "last_name")
    val lastName: String
)