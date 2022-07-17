package com.nisaefendioglu.passwordgenerator.model


import com.google.gson.annotations.SerializedName

data class PasswordModel(
    @SerializedName("char")
    val char: List<String>
)