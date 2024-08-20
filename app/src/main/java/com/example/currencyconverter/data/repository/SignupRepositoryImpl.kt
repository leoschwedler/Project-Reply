package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.db.UserDAO
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.repository.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val userDAO: UserDAO) : SignupRepository {
    override suspend fun signUp(user: User): Long {
        return userDAO.insertUser(user)
    }
}