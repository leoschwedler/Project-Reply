package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    // LiveData para observar o resultado do signup
    private val _signUpResult = MutableLiveData<Long>()
    val signUpResult: LiveData<Long> get() = _signUpResult


    private val _inputValidationError = MutableLiveData<String>()
    val inputValidationError: LiveData<String> get() = _inputValidationError

    fun signUp(user: User) {
        viewModelScope.launch {
            val result = signupUseCase.invoke(user)
            _signUpResult.value = result
        }
    }

    fun validateInputs(username: String, email: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                _inputValidationError.value = "Please enter a username"
                false
            }
            email.isEmpty() -> {
                _inputValidationError.value = "Please enter an email"
                false
            }
            password.isEmpty() -> {
                _inputValidationError.value = "Please enter a password"
                false
            }
            password.length < 6 -> {
                _inputValidationError.value = "Password must be at least 6 characters long"
                false
            }
            else -> true
        }
    }
}
