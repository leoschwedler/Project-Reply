package com.example.currencyconverter.data.repository


import com.example.currencyconverter.data.dto.toCurrencyChangeQueries
import com.example.currencyconverter.data.remote.ExchangeRateApi
import com.example.currencyconverter.domain.model.CurrencyChangeQueriesDomain
import com.example.currencyconverter.domain.repository.DateRepository
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : DateRepository {
    override suspend fun getDate(source: String, startDate: String, endDate: String): CurrencyChangeQueriesDomain {
        return try {
            val response = exchangeRateApi.getDate(source, startDate, endDate)
            if (response.isSuccessful) {
                val responseCurrencyChangeQueries = response.body()
                responseCurrencyChangeQueries?.toCurrencyChangeQueries()
                    ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalStateException("Response is not successful")
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch date", e)
        }
    }
}
