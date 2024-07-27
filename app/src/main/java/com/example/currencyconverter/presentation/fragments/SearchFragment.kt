package com.example.currencyconverter.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.data.dto.CurrenciesResponse
import com.example.currencyconverter.data.dto.ExchangeRateItem
import com.example.currencyconverter.data.dto.ExchangeRateResponse
import com.example.currencyconverter.data.remote.RetrofitHelper
import com.example.currencyconverter.databinding.FragmentSearchBinding
import com.example.currencyconverter.presentation.adapter.ExchangeRateAdapter
import com.example.currencyconverter.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val exchangerate by lazy { RetrofitHelper.exchangeRateAPI }
    private var currencyList: List<String> = listOf()
    private val exchangeRateAdapter by lazy { ExchangeRateAdapter(emptyList()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuração do RecyclerView
        binding.currencyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.currencyRecyclerView.adapter = exchangeRateAdapter

        // Carregar dados iniciais e configurar listeners
        CoroutineScope(Dispatchers.IO).launch {
            currencies()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    fetchExchangeRates()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun currencies() {
        var retorno: Response<CurrenciesResponse>? = null
        try {
            retorno = exchangerate.currencies()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (retorno != null && retorno.isSuccessful) {
            val currenciesResponse = retorno.body()
            val currencies = currenciesResponse?.currencies?.keys?.toList() ?: listOf()
            withContext(Dispatchers.Main) {
                updateSpinners(currencies)
            }
        }
    }

    private fun updateSpinners(currencies: List<String>) {
        currencyList = currencies
        val adapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner,
            currencyList
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spinner.adapter = adapter
    }

    private suspend fun fetchExchangeRates() {
        var retorno: Response<ExchangeRateResponse>? = null
        val sourceCurrency = binding.spinner.selectedItem.toString()
        try {
            retorno = exchangerate.getExchangeRates(Const.API_KEY, sourceCurrency)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (retorno != null && retorno.isSuccessful) {
            val exchangeRates = retorno.body()?.quotes?.map { (key, value) ->
                ExchangeRateItem(currencyPair = key, rate = value)
            } ?: emptyList()

            withContext(Dispatchers.Main) {
                exchangeRateAdapter.updateRates(exchangeRates)
            }
        }
    }
}
