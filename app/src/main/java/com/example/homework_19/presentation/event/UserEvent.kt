package com.example.homework_19.presentation.event

sealed class UserEvent{
    data class GetUser(val id: Int): UserEvent()
}