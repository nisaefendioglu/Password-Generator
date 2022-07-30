package com.nisaefendioglu.passwordgenerator.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.passwordgenerator.data.remote.utils.DataState
import com.nisaefendioglu.passwordgenerator.domain.repository.PasswordRepository
import com.nisaefendioglu.passwordgenerator.domain.viewstate.main.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState())
    val uiState: StateFlow<MainViewState> = _uiState

    private val _uiEvent: MutableSharedFlow<MainViewEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getPasswordGenerator(MainViewState())
    }

    fun onTriggerEvent(event: MainViewEvent) {
        when (event) {
            is MainViewEvent.GeneratePassword -> getPasswordGenerator(event.viewState)
        }
    }

    private fun getPasswordGenerator(viewState: MainViewState) {
        viewModelScope.launch {
            passwordRepository.getPasswordGenerator().collect {
                when (it) {
                    is DataState.Success -> {
                        val generatedPassword =
                            it.data.char.toString().replace("[", "").replace("]", "")

                        prepareViewState(viewState, generatedPassword)

                        _uiState.value = _uiState.value.copy(char = it.data.char)
                    }
                    is DataState.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = it.apiError?.message ?: "Unknown error"
                        )
                        Log.d("Password Response Fail", it.apiError?.message.toString())
                    }
                    is DataState.Loading -> {}
                }
            }
        }
    }

    private fun prepareViewState(viewState: MainViewState, generatedPassword: String) {
        viewModelScope.launch {
            if (viewState.isUpperCase != null || viewState.isLowerCase != null) {
                viewState.isUpperCase?.let {
                    if (it) {
                        _uiState.value = _uiState.value.copy(
                            passwordGenerator = generatedPassword.uppercase(Locale.getDefault())
                        )

                    }
                }

                viewState.isLowerCase?.let {
                    if (it) {
                        _uiState.value = _uiState.value.copy(
                            passwordGenerator = generatedPassword.lowercase(Locale.getDefault())
                        )
                    }
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    passwordGenerator = generatedPassword
                )
            }
        }
    }
}

sealed class MainViewEvent {
    class GeneratePassword(val viewState: MainViewState) : MainViewEvent()
}