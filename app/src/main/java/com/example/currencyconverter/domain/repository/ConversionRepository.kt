package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyConversionDomain

interface ConversionRepository {
    suspend fun convertCurrency(from: String, to: String, amount: Double): CurrencyConversionDomain
}