package com.example.homework_19.presentation.event

sealed class UsersEvent{
    data object GetAllUser: UsersEvent()
}