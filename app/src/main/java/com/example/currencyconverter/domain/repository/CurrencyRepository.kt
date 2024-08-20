package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrenciesDomain

interface CurrencyRepository {
    suspend fun getCurrencies(): CurrenciesDomain
}