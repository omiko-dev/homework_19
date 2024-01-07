package com.example.homework_19.data.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ResourceHandler {

    fun <T : Any> resourceHandler(call: suspend () -> Response<T>): Flow<Resource<T>> {
        return flow {
            try{
                emit(Resource.Loader)
                val user = call()
                if (user.isSuccessful) {
                    user.body()?.let {
                        emit(Resource.Success(it))
                    }
                } else {
                    emit(Resource.Error(error = user.errorBody()?.string() ?: ""))
                }
            }catch (e: Throwable){
                Log.i("omiko", e.message.toString())
            }finally {
                emit(Resource.Idle)
            }
        }
    }

}