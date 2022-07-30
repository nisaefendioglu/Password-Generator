package com.nisaefendioglu.passwordgenerator.domain.viewstate.main

data class MainViewState(
    val errorMessage: String? = null,
    val passwordGenerator: String? = null,
    val char: List<String>? = null,
    val isUpperCase: Boolean? = null,
    val isLowerCase: Boolean? = null
)