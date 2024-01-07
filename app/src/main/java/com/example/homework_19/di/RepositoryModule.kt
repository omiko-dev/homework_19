package com.example.homework_19.di

import com.example.homework_19.data.repository.UserRepositoryImpl
import com.example.homework_19.data.service.UserService
import com.example.homework_19.data.service.UsersService
import com.example.homework_19.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        usersService: UsersService
    ): UserRepository {
        return UserRepositoryImpl(usersService = usersService, userService = userService)
    }


}