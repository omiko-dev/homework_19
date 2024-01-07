package com.example.homework_19.domain.repository

import com.example.homework_19.data.common.Resource
import com.example.homework_19.domain.model.UserListModel
import com.example.homework_19.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id: Int): Flow<Resource<UserModel>>

    suspend fun getAllUser(): Flow<Resource<List<UserListModel>>>
}