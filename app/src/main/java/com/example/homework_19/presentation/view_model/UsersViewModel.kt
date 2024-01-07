package com.example.homework_19.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_19.data.common.Resource
import com.example.homework_19.domain.use_case.GetUsersUseCase
import com.example.homework_19.presentation.event.UsersEvent
import com.example.homework_19.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersUseCase: GetUsersUseCase): ViewModel() {

    private var _usersStateFlow = MutableStateFlow<Resource<List<User>>>(Resource.Idle)
    val usersStateFlow = _usersStateFlow.asStateFlow()

    fun onEvent(event: UsersEvent){
        when(event){
            is UsersEvent.GetAllUser -> getAllUser()
        }
    }

    private fun getAllUser(){
        viewModelScope.launch {
            usersUseCase.invoke().collect {
                _usersStateFlow.value = it
            }
        }
    }
}