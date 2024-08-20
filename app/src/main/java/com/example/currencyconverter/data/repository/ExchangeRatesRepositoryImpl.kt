package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.dto.toExchangeRateDomain
import com.example.currencyconverter.data.remote.ExchangeRateApi
import com.example.currencyconverter.domain.model.ExchangeRateDomain
import com.example.currencyconverter.domain.repository.ExchangeRatesRepository
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : ExchangeRatesRepository {

    override suspend fun getExchangeRates(baseCurrency: String): ExchangeRateDomain {
        return try {
            val response = exchangeRateApi.getExchangeRates(baseCurrency)
            if (response.isSuccessful) {
                val responseExchangeRates = response.body()
                responseExchangeRates?.toExchangeRateDomain()
                    ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalStateException("Response is not successful")
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch exchange rates", e)
        }
    }
}
