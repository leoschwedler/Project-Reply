package com.example.currencyconverter.data.dto

import com.example.currencyconverter.domain.model.ExchangeRateDomain


data class ExchangeRateResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

fun ExchangeRateResponse.toExchangeRateDomain(): ExchangeRateDomain {
    return ExchangeRateDomain(
        success = this.success,
        terms = this.terms,
        privacy = this.privacy,
        timestamp = this.timestamp,
        source = this.source,
        quotes = this.quotes
    )
}

