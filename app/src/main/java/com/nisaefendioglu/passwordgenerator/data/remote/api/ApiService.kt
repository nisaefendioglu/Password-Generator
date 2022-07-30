package com.nisaefendioglu.passwordgenerator.data.remote.api

import com.nisaefendioglu.passwordgenerator.helper.Constants
import com.nisaefendioglu.passwordgenerator.data.model.PasswordResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.END_POINT)
   suspend fun getPasswordGenerator(): Response<PasswordResponse>
}