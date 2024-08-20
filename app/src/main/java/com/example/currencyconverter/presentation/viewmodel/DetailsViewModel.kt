package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.CurrencyChangeQueriesDomain
import com.example.currencyconverter.domain.usecase.GetDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDateUseCase: GetDateUseCase
) : ViewModel() {

    // LiveData para o modelo de domínio
    private val _currencyChangeQueriesDomain = MutableLiveData<CurrencyChangeQueriesDomain?>()
    val currencyChangeQueriesDomain: LiveData<CurrencyChangeQueriesDomain?> = _currencyChangeQueriesDomain

    // LiveData para erros ou status de carregamento
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchData(source: String, period: String) {
        viewModelScope.launch {
            try {
                val (startDate, endDate) = getDateRange(period)
                val result = getDateUseCase(source, startDate, endDate)
                _currencyChangeQueriesDomain.value = result
                _error.value = null // Limpar qualquer erro anterior
            } catch (e: Exception) {
                // Definir o erro na LiveData
                _error.value = "Failed to fetch data: ${e.message}"
                _currencyChangeQueriesDomain.value = null // Limpar dados anteriores
            }
        }
    }

    private fun getDateRange(period: String): Pair<String, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val now = LocalDate.now()

        val startDate = when (period) {
            "24 hrs" -> now.minus(1, ChronoUnit.DAYS)
            "48 hrs" -> now.minus(2, ChronoUnit.DAYS)
            "7 days" -> now.minus(7, ChronoUnit.DAYS)
            "30 days" -> now.minus(30, ChronoUnit.DAYS)
            "90 days" -> now.minus(90, ChronoUnit.DAYS)
            "1 year" -> now.minus(1, ChronoUnit.YEARS)
            else -> now.minus(1, ChronoUnit.DAYS) // Valor padrão para períodos desconhecidos
        }.format(formatter)

        val endDate = now.format(formatter)

        return Pair(startDate, endDate)
    }
}
