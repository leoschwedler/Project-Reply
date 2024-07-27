package com.example.currencyconverter.data.dto

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)