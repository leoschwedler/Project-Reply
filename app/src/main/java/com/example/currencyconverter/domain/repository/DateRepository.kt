package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyChangeQueriesDomain

interface DateRepository {
    suspend fun getDate(
        source: String,
        startDate: String,
        endDate: String
    ): CurrencyChangeQueriesDomain
}