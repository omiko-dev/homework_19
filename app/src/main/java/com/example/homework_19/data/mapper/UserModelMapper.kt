package com.example.homework_19.data.mapper

import com.example.homework_19.data.dto.UserDto
import com.example.homework_19.domain.model.UserModel

fun UserDto.toDomain(): UserModel {
    return UserModel(
        data = data.toDomain()
    )
}