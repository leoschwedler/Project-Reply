package com.example.currencyconverter.data.remote


import com.example.currencyconverter.data.dto.CurrencyConversionResponse
import com.example.currencyconverter.data.dto.CurrenciesResponse
import com.example.currencyconverter.data.dto.CurrencyChangeQueriesResponse
import com.example.currencyconverter.data.dto.ExchangeRateResponse
import com.example.currencyconverter.util.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRateApi {

    @GET("convert?access_key=${Const.API_KEY}")
    suspend fun getCurrencyConversion(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<CurrencyConversionResponse>

    @GET("list?access_key=${Const.API_KEY}")
    suspend fun getCurrencies(): Response<CurrenciesResponse>

    @GET("live?access_key=${Const.API_KEY}")
    suspend fun getExchangeRates(
        @Query("source") source: String
    ): Response<ExchangeRateResponse>

    @GET("change?access_key=${Const.API_KEY}")
    suspend fun getDate(
        @Query("source") currency: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<CurrencyChangeQueriesResponse>
}