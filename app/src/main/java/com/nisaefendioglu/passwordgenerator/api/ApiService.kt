package com.nisaefendioglu.passwordgenerator.api

import com.nisaefendioglu.passwordgenerator.helper.Constants
import com.nisaefendioglu.passwordgenerator.model.PasswordModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getPasswordGenerator(): Response<PasswordModel>

}