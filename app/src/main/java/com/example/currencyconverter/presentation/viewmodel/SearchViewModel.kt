package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.dto.ExchangeRateItem

import com.example.currencyconverter.domain.usecase.GetCurrenciesUseCase
import com.example.currencyconverter.domain.usecase.GetExchangeRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    private val _currencies = MutableLiveData<List<String>>()
    val currencies: LiveData<List<String>> = _currencies

    private val _exchangeRates = MutableLiveData<List<ExchangeRateItem>>()
    val exchangeRates: LiveData<List<ExchangeRateItem>> = _exchangeRates

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            try {
                val response = getCurrenciesUseCase()
                _currencies.value = response?.currencies?.keys?.toList() ?: emptyList()
            } catch (e: Exception) {
                // Handle errors, maybe update a LiveData for errors if necessary
                e.printStackTrace()
            }
        }
    }

    fun fetchExchangeRates(baseCurrency: String) {
        viewModelScope.launch {
            try {
                val response = getExchangeRatesUseCase(baseCurrency)
                val exchangeRateItems = response?.quotes?.map { (key, value) ->
                    ExchangeRateItem(currencyPair = key, rate = value)
                } ?: emptyList()
                _exchangeRates.value = exchangeRateItems
            } catch (e: Exception) {
                // Handle errors, maybe update a LiveData for errors if necessary
                e.printStackTrace()
            }
        }
    }

    fun updateSpinners(currencies: List<String>) {
        _currencies.value = currencies
    }
}
