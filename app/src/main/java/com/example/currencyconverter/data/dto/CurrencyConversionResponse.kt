package com.example.currencyconverter.data.dto

data class CurrencyConversionResponse(
    val info: Info,
    val privacy: String,
    val query: Query,
    val result: Double,
    val success: Boolean,
    val terms: String
)