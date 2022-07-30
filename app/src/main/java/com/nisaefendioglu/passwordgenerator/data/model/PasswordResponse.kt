package com.nisaefendioglu.passwordgenerator.data.model

import com.google.gson.annotations.SerializedName

data class PasswordResponse(
    @SerializedName("char")
    val char: List<String>
)