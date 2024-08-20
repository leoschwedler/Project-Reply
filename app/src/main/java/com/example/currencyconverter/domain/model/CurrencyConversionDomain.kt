package com.example.currencyconverter.domain.model

import com.example.currencyconverter.data.dto.Info
import com.example.currencyconverter.data.dto.Query

data class CurrencyConversionDomain(
    val info: Info,
    val privacy: String,
    val query: Query,
    val result: Double,
    val success: Boolean,
    val terms: String
)
