package com.example.currencyconverter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.data.dto.ExchangeRateItem
import com.example.currencyconverter.databinding.CurrencyItemLayoutBinding


class ExchangeRateAdapter(private var rates: List<ExchangeRateItem>) :
    RecyclerView.Adapter<ExchangeRateAdapter.ExchangeRateViewHolder>() {

    inner class ExchangeRateViewHolder(private val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rateItem: ExchangeRateItem) {
            binding.currencyNameTextView.text = rateItem.currencyPair
            binding.currencyPriceTextView.text = rateItem.rate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val binding = CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExchangeRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        val rateItem = rates[position]
        holder.bind(rateItem)
    }

    override fun getItemCount(): Int = rates.size

    // Novo método para atualizar a lista de taxas
    fun updateRates(newRates: List<ExchangeRateItem>) {
        rates = newRates
        notifyDataSetChanged() // Notifica que os dados foram atualizados
    }
}
