package com.example.homework_19.data.mapper

import com.example.homework_19.data.dto.UsersDto
import com.example.homework_19.domain.model.UserListModel

fun UsersDto.toDomain(): UserListModel{
    return UserListModel(
        avatar = avatar,
        email = email,
        firstName = firstName,
        id = id,
        lastName = lastName
    )
}