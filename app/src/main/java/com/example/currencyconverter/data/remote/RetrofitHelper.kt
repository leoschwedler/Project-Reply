package com.example.currencyconverter.data.remote

import com.example.currencyconverter.util.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {


    val retrofit = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val exchangeRateAPI = retrofit.create(ExchangeRateApi::class.java)
}