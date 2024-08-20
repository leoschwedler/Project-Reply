package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.repository.SignupRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signupRepository: SignupRepository) {
    suspend fun invoke(user: User): Long {
        return signupRepository.signUp(user)
    }
}