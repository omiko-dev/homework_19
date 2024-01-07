package com.example.homework_19.data.service

import com.example.homework_19.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserDto>
}