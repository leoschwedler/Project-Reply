package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.db.UserDAO
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val userDAO: UserDAO) : LoginRepository {
    override suspend fun login(user: User): Boolean {
        return userDAO.readUser(user)
    }
}