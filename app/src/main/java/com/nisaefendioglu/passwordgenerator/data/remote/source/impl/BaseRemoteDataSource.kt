package com.nisaefendioglu.passwordgenerator.data.remote.source.impl

import com.google.gson.Gson
import com.nisaefendioglu.passwordgenerator.data.remote.utils.DataState
import com.nisaefendioglu.passwordgenerator.data.model.ApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response

open class BaseRemoteDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Flow<DataState<T>> {
        return flow<DataState<T>> {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) emit(DataState.Success(body))
                else {
                    val apiError: ApiError =
                        Gson().fromJson(response.errorBody()?.charStream(), ApiError::class.java)
                    emit(DataState.Error(apiError))
                }
            } else {
                val apiError: ApiError =
                    Gson().fromJson(response.errorBody()?.charStream(), ApiError::class.java)
                emit(DataState.Error(apiError))
            }

        }
            .catch {
                emit(DataState.Error(ApiError(-1, it.message ?: it.toString())))
            }
            .onStart { emit(DataState.Loading()) }
            .flowOn(Dispatchers.IO)
    }
}