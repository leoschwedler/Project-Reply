package com.example.currencyconverter.data.dto

import com.example.currencyconverter.domain.model.CurrenciesDomain

data class CurrenciesResponse(
    val currencies: Map<String, String>,
    val privacy: String,
    val success: Boolean,
    val terms: String
)
fun CurrenciesResponse.toCurrenciesDomain(): CurrenciesDomain{
   return CurrenciesDomain(
        currencies = this.currencies,
        privacy = this.privacy,
        success = this.success,
        terms = this.terms
    )
}