package com.nisaefendioglu.passwordgenerator.api

import com.nisaefendioglu.passwordgenerator.helper.Constants
import com.nisaefendioglu.passwordgenerator.model.PasswordResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    fun getPasswordGenerator(): Call<PasswordResponse>

}