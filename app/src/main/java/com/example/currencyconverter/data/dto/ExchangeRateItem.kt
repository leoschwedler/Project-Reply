package com.example.currencyconverter.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExchangeRateItem(
    val currencyPair: String,
    val rate: Double
): Parcelable
