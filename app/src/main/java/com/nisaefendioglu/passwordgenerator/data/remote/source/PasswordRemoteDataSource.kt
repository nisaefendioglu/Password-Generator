package com.nisaefendioglu.passwordgenerator.data.remote.source

import com.nisaefendioglu.passwordgenerator.data.remote.utils.DataState
import com.nisaefendioglu.passwordgenerator.data.model.PasswordResponse
import kotlinx.coroutines.flow.Flow

interface PasswordRemoteDataSource {
    suspend fun getPasswordGenerator(): Flow<DataState<PasswordResponse>>
}