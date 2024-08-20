package com.example.currencyconverter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentHomeBinding
import com.example.currencyconverter.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currencies.observe(viewLifecycleOwner, Observer { currencies ->
            updateSpinners(currencies)
        })


        viewModel.conversionResult.observe(viewLifecycleOwner, Observer { result ->
            binding.textViewResult.text = result?.toString() ?: ""
        })


        viewModel.quote.observe(viewLifecycleOwner, Observer { quote ->
            binding.textViewQuote.text = quote ?: ""
        })


        binding.buttonConvert.setOnClickListener {
            val from = binding.spinner.selectedItem.toString()
            val to = binding.spinner2.selectedItem.toString()
            val amount = binding.editTextAmout.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.convertCurrency(from, to, amount)
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
        binding.spinner2.adapter = adapter
    }
}
