package com.example.homework_19.data.common

sealed class Resource<out T>{
    data class Success<out T>(val success: T): Resource<T>()
    data class Error(val error: String): Resource<Nothing>()
    data object Loader: Resource<Nothing>()
    data object Idle: Resource<Nothing>()
}
