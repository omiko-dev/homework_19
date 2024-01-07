package com.example.homework_19.di

import com.example.homework_19.data.service.UserService
import com.example.homework_19.data.service.UsersService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsersRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    private const val USERS_BASE_URL = "https://run.mocky.io/v3/"
    private const val USER_BASE_URL = "https://reqres.in/api/"

    @Provides
    @Singleton
    @UsersRetrofit
    fun provideUsersRetrofitClient(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(USERS_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()

    @Provides
    @Singleton
    @UserRetrofit
    fun provideUserRetrofitClient(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(USER_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()

    @Provides
    @Singleton
    fun provideUsersService(@UsersRetrofit retrofit: Retrofit): UsersService =
        retrofit.create(UsersService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@UserRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)


}