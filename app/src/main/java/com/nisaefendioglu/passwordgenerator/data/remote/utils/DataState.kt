package com.nisaefendioglu.passwordgenerator.data.remote.utils

import com.nisaefendioglu.passwordgenerator.data.model.ApiError

sealed class DataState<T> {
    class Success<T>(val data: T) : DataState<T>()
    class Loading<T> : DataState<T>()
    class Error<T>(val apiError: ApiError?) : DataState<T>()
}