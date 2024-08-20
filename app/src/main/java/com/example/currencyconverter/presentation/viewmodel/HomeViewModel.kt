package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.example.currencyconverter.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _currencies = MutableLiveData<List<String>>()
    val currencies: LiveData<List<String>> = _currencies

    private val _conversionResult = MutableLiveData<Double?>()
    val conversionResult: LiveData<Double?> = _conversionResult

    private val _quote = MutableLiveData<String?>()
    val quote: LiveData<String?> = _quote

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            try {
                val response = getCurrenciesUseCase()
                _currencies.value = response?.currencies?.keys?.toList() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun convertCurrency(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            try {
                val response = convertCurrencyUseCase(from, to, amount)
                _conversionResult.value = response?.result
                _quote.value = response?.info?.quote?.let { "1 $from = $it" }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
