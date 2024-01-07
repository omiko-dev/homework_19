package com.example.homework_19.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_19.data.common.Resource
import com.example.homework_19.domain.use_case.GetUserUseCase
import com.example.homework_19.presentation.event.UserEvent
import com.example.homework_19.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase): ViewModel() {
    private var _userStateFlow = MutableStateFlow<Resource<User>>(Resource.Idle)
    val userStateFlow get() = _userStateFlow.asStateFlow()

    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.GetUser -> getUser(event.id)
        }
    }

    private fun getUser(id: Int){
        viewModelScope.launch {
            getUserUseCase.invoke(id).collect{
                _userStateFlow.value = it
            }
        }
    }
}