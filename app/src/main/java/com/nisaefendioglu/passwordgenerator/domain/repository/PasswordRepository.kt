package com.nisaefendioglu.passwordgenerator.domain.repository

import com.nisaefendioglu.passwordgenerator.data.remote.utils.DataState
import com.nisaefendioglu.passwordgenerator.data.model.PasswordResponse
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun getPasswordGenerator(): Flow<DataState<PasswordResponse>>
}