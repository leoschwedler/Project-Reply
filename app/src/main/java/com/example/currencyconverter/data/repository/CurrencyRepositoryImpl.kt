package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.dto.toCurrenciesDomain // Função de extensão para conversão para o modelo de domínio
import com.example.currencyconverter.data.remote.ExchangeRateApi
import com.example.currencyconverter.domain.model.CurrenciesDomain
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : CurrencyRepository {
    override suspend fun getCurrencies(): CurrenciesDomain {
        return try {
            val response = exchangeRateApi.getCurrencies()
            if (response.isSuccessful) {
                val responseCurrencies = response.body()
                responseCurrencies?.toCurrenciesDomain()
                    ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalStateException("Response is not successful")
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch currencies", e)
        }
    }
}
