package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.CurrenciesDomain
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): CurrenciesDomain {
        return try {
            repository.getCurrencies()
        } catch (e: Exception) {
            throw RuntimeException("Error fetching currencies data", e)
        }
    }
}
