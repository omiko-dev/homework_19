package com.example.homework_19.presentation.model

data class User(
    val id: Int,
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    var isSelected: Boolean = false
)
