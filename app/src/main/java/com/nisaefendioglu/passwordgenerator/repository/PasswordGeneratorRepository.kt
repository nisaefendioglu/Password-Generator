package com.nisaefendioglu.passwordgenerator.repository
import com.nisaefendioglu.passwordgenerator.api.ApiService
import javax.inject.Inject

class PasswordGeneratorRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPasswordGenerate() = apiService.getPasswordGenerator()
}