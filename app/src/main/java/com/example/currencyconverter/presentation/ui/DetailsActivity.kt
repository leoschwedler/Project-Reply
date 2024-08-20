package com.example.currencyconverter.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.data.dto.CurrencyDetails
import com.example.currencyconverter.databinding.ActivityDetailsBinding
import com.example.currencyconverter.presentation.adapter.DetailsAdapter
import com.example.currencyconverter.presentation.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private lateinit var adapter: DetailsAdapter
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val bundle = intent.extras
        val rate = bundle?.getDouble("rate")
        val spinner = bundle?.getString("spinner")

        binding.detailCoinTextView.text = spinner
        binding.detailPriceTextView.text = rate.toString()

        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DetailsAdapter(emptyList())
        binding.detailRecyclerView.adapter = adapter

        binding.buttonOneYear.setOnClickListener { fetchData(spinner, "1 year") }
        binding.buttonNinetyDays.setOnClickListener { fetchData(spinner, "90 days") }
        binding.buttonThirtyDays.setOnClickListener { fetchData(spinner, "30 days") }
        binding.buttonSevenDays.setOnClickListener { fetchData(spinner, "7 days") }
        binding.buttonFortyEightHours.setOnClickListener { fetchData(spinner, "48 hrs") }
        binding.buttonTwentyFourHours.setOnClickListener { fetchData(spinner, "24 hrs") }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        detailsViewModel.currencyChangeQueriesDomain.observe(this) { queries ->
            queries?.let {
                updateRecyclerView(it.quotes)
            }
        }

        detailsViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                showError(it)
            }
        }
    }

    private fun fetchData(source: String?, period: String) {
        source?.let {
            detailsViewModel.fetchData(it, period)
        }
    }

    private fun updateRecyclerView(quotes: Map<String, CurrencyDetails>) {
        val currencyList = quotes.toList()
        adapter.updateData(currencyList)
    }

    private fun showError(message: String) {
    }
}
