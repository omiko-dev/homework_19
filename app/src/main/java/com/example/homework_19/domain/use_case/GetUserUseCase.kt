package com.example.homework_19.domain.use_case

import com.example.homework_19.data.common.Resource
import com.example.homework_19.data.common.resourceMapper
import com.example.homework_19.domain.mapper.toPresentation
import com.example.homework_19.domain.repository.UserRepository
import com.example.homework_19.presentation.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<User>> =
        userRepository.getUser(id).map {
            it.resourceMapper { user ->
                user.toPresentation()
            }
        }
}