package com.example.currencyconverter.domain.model

data class CurrenciesDomain(
    val currencies: Map<String, String>,
    val privacy: String,
    val success: Boolean,
    val terms: String
)
