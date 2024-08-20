package com.example.currencyconverter.domain.model

import com.example.currencyconverter.data.dto.CurrencyDetails

data class CurrencyChangeQueriesDomain(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val start_date: String,
    val end_date: String,
    val source: String,
    val quotes: Map<String, CurrencyDetails>
)
