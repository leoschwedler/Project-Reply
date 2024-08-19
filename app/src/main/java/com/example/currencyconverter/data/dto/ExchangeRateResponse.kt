package com.example.currencyconverter.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ExchangeRateResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

