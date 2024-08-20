package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.dto.toCurrencyConversionDomain
import com.example.currencyconverter.data.remote.ExchangeRateApi
import com.example.currencyconverter.domain.model.CurrencyConversionDomain
import com.example.currencyconverter.domain.repository.ConversionRepository
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : ConversionRepository {
    override suspend fun convertCurrency(from: String, to: String, amount: Double): CurrencyConversionDomain {
        return try {
            val response = exchangeRateApi.getCurrencyConversion(from, to, amount)
            if (response.isSuccessful) {
                val responseCurrencyConversion = response.body()
                responseCurrencyConversion?.toCurrencyConversionDomain()
                    ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalStateException("Response is not successful")
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert currency", e)
        }
    }
}