package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.model.User

interface SignupRepository {
    suspend fun signUp(user: User): Long
}