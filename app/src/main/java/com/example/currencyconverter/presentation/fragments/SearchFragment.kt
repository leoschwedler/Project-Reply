package com.example.currencyconverter.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentSearchBinding
import com.example.currencyconverter.presentation.adapter.ExchangeRateAdapter
import com.example.currencyconverter.presentation.ui.DetailsActivity
import com.example.currencyconverter.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var exchangeRateAdapter: ExchangeRateAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView Adapter
        exchangeRateAdapter = ExchangeRateAdapter(emptyList()) { exchangeRateItem ->
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            val spinner = binding.spinner.selectedItem.toString()
            intent.putExtra("rate", exchangeRateItem.rate)
            intent.putExtra("spinner", spinner)
            startActivity(intent)
        }
        binding.currencyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.currencyRecyclerView.adapter = exchangeRateAdapter

        // Observe LiveData from ViewModel
        viewModel.currencies.observe(viewLifecycleOwner, Observer { currencies ->
            updateSpinners(currencies)
        })

        viewModel.exchangeRates.observe(viewLifecycleOwner, Observer { exchangeRates ->
            exchangeRateAdapter.updateRates(exchangeRates)
        })

        // Fetch currencies initially
        viewModel.fetchCurrencies()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val baseCurrency = binding.spinner.selectedItem.toString()
                viewModel.fetchExchangeRates(baseCurrency)
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

    private fun updateSpinners(currencies: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner,
            currencies
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spinner.adapter = adapter
    }
}
