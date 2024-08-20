package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.ExchangeRateDomain
import com.example.currencyconverter.domain.repository.ExchangeRatesRepository
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val repository: ExchangeRatesRepository
) {
    suspend operator fun invoke(baseCurrency: String): ExchangeRateDomain {
        return try {
            repository.getExchangeRates(baseCurrency)
        } catch (e: Exception) {
            throw RuntimeException("Error fetching exchange rates", e)
        }
    }
}
