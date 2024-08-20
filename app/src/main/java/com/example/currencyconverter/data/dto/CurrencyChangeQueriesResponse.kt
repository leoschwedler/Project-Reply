package com.example.currencyconverter.data.dto

import com.example.currencyconverter.domain.model.CurrencyChangeQueriesDomain

data class CurrencyChangeQueriesResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val start_date: String,
    val end_date: String,
    val source: String,
    val quotes: Map<String, CurrencyDetails>
)

fun CurrencyChangeQueriesResponse.toCurrencyChangeQueries(): CurrencyChangeQueriesDomain{
    return CurrencyChangeQueriesDomain(
        success = this.success,
        terms = this.terms,
        privacy = this.privacy,
        start_date = this.start_date,
        end_date = this.end_date,
        source = this.source,
        quotes = this.quotes
    )
}
