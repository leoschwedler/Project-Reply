package com.example.currencyconverter.data.dto

data class CurrencyDetails (
    val start_rate: Double,
    val end_rate: Double,
    val change: Double,
    val change_pct: Double
)