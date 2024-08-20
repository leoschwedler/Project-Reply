package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.ExchangeRateDomain

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(baseCurrency: String): ExchangeRateDomain
}