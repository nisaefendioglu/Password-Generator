package com.nisaefendioglu.passwordgenerator.api

import com.nisaefendioglu.passwordgenerator.helper.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun create(java: Class<ApiService>): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}