package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    private val _inputValidationError = MutableLiveData<String>()
    val inputValidationError: LiveData<String>
        get() = _inputValidationError

    fun login(user: User) {
        viewModelScope.launch {
            val success = loginUseCase.invoke(user)
            _loginSuccess.value = success
        }
    }

    fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                _inputValidationError.value = "Please enter an email"
                false
            }
            password.isEmpty() -> {
                _inputValidationError.value = "Please enter a password"
                false
            }
            else -> true
        }
    }
}
