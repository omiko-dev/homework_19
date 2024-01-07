package com.example.homework_19.data.repository

import com.example.homework_19.data.common.Resource
import com.example.homework_19.data.common.ResourceHandler
import com.example.homework_19.data.common.resourceMapper
import com.example.homework_19.data.mapper.toDomain
import com.example.homework_19.data.service.UserService
import com.example.homework_19.data.service.UsersService
import com.example.homework_19.domain.model.UserListModel
import com.example.homework_19.domain.model.UserModel
import com.example.homework_19.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
    private val userService: UserService,
    private val resourceHandler: ResourceHandler = ResourceHandler()

) : UserRepository {
    override suspend fun getUser(id: Int): Flow<Resource<UserModel>> {
        return resourceHandler.resourceHandler {
            userService.getUser(id)
        }.map {
            it.resourceMapper { user ->
                user.toDomain()
            }
        }
    }

    override suspend fun getAllUser(): Flow<Resource<List<UserListModel>>> {
        return resourceHandler.resourceHandler {
            usersService.getUsers()
        }.map {
            it.resourceMapper { userList ->
                userList.map { user ->
                    user.toDomain()
                }
            }
        }
    }
}