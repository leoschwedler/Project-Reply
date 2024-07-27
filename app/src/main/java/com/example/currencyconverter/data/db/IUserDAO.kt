package com.example.currencyconverter.data.db

import com.example.currencyconverter.data.model.User

interface IUserDAO {
    fun insertUser(user: User): Long
    fun readUser(user: User): Boolean
}