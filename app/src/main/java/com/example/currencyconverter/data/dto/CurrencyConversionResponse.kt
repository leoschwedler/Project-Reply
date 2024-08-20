package com.example.currencyconverter.data.dto

import com.example.currencyconverter.domain.model.CurrencyConversionDomain

data class CurrencyConversionResponse(
    val info: Info,
    val privacy: String,
    val query: Query,
    val result: Double,
    val success: Boolean,
    val terms: String
)
fun CurrencyConversionResponse.toCurrencyConversionDomain(): CurrencyConversionDomain {
    return CurrencyConversionDomain(
        info = this.info,
        privacy = this.privacy,
        query = this.query,
        result = this.result,
        success = this.success,
        terms = this.terms
    )
}