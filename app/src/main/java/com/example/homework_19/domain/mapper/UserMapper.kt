package com.example.homework_19.domain.mapper

import com.example.homework_19.domain.model.UserListModel
import com.example.homework_19.domain.model.UserModel
import com.example.homework_19.presentation.model.User

fun UserModel.toPresentation(): User =
    User(
        id = data.id,
        avatar = data.avatar,
        email = data.email,
        firstName = data.firstName,
        lastName = data.lastName
    )

fun UserListModel.toPresentation(): User =
    User(
        id = id,
        avatar = avatar,
        email = email,
        firstName = firstName,
        lastName = lastName
    )