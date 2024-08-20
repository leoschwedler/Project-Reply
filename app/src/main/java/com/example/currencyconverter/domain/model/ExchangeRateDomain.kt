package com.example.currencyconverter.domain.model

data class ExchangeRateDomain(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)
