package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.CurrencyConversionDomain
import com.example.currencyconverter.domain.repository.ConversionRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val conversionRepository: ConversionRepository
) {
    suspend operator fun invoke(
        from: String,
        to: String,
        amount: Double
    ): CurrencyConversionDomain {
        return try {
            conversionRepository.convertCurrency(from, to, amount)
        } catch (e: Exception) {
            throw RuntimeException("Error during currency conversion", e)
        }
    }
}
