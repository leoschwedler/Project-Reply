package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.CurrencyChangeQueriesDomain
import com.example.currencyconverter.domain.repository.DateRepository
import javax.inject.Inject

class GetDateUseCase @Inject constructor(
    private val repository: DateRepository
) {
    suspend operator fun invoke(
        source: String,
        startDate: String,
        endDate: String
    ): CurrencyChangeQueriesDomain {
        return try {
            repository.getDate(source, startDate, endDate)
        } catch (e: Exception) {
            throw RuntimeException("Error fetching date range data", e)
        }
    }
}
