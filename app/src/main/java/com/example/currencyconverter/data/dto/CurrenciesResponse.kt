package com.example.currencyconverter.data.dto

data class CurrenciesResponse(
    val currencies: Map<String, String>,
    val privacy: String,
    val success: Boolean,
    val terms: String
)