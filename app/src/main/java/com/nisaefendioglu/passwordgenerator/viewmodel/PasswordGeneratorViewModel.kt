package com.nisaefendioglu.passwordgenerator.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.passwordgenerator.model.PasswordModel
import com.nisaefendioglu.passwordgenerator.repository.PasswordGeneratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordGeneratorViewModel @Inject constructor(private val repository: PasswordGeneratorRepository): ViewModel() {

    private val _response = MutableLiveData<List<PasswordModel>>()
    val responsePassword: LiveData<List<PasswordModel>>
        get() = _response

    init {
        getPassword()
    }

    private fun getPassword() = viewModelScope.launch {
        repository.getPasswordGenerate().let {response ->
            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
                Log.d("tag", "getPassword Error: ${response.code()}")
            }
        }
    }

}


