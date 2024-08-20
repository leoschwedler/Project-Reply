package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun invoke(user: User): Boolean {
        return loginRepository.login(user)
    }
}