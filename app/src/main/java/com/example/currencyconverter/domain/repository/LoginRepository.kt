package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.model.User

interface LoginRepository {
    suspend fun login(user: User): Boolean
}