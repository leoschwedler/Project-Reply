package com.example.currencyconverter.presentation.fragments

import android.content.Intent
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
import com.example.currencyconverter.presentation.ui.DetailsActivity
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
    private lateinit var exchangeRateAdapter: ExchangeRateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exchangeRateAdapter = ExchangeRateAdapter(emptyList()) { exchangeRateItem ->
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            val spinner = binding.spinner.selectedItem.toString()
            intent.putExtra("rate", exchangeRateItem.rate)
            intent.putExtra("spinner",spinner)
            startActivity(intent)
        }
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
        val spinner = binding.spinner.selectedItem.toString()
        try {
            retorno = exchangerate.getExchangeRates(spinner)
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
