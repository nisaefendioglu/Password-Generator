package com.nisaefendioglu.passwordgenerator.data.remote.source.impl

import com.nisaefendioglu.passwordgenerator.data.remote.api.ApiService
import com.nisaefendioglu.passwordgenerator.data.remote.source.PasswordRemoteDataSource
import javax.inject.Inject

class PasswordRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    BaseRemoteDataSource(), PasswordRemoteDataSource {
    override suspend fun getPasswordGenerator() = getResult {
        apiService.getPasswordGenerator()
    }
}