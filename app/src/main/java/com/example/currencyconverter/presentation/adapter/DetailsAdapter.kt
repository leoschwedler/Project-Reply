package com.example.currencyconverter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.data.dto.CurrencyDetails
import com.example.currencyconverter.databinding.CurrencyItemDetailsLayoutBinding

class DetailsAdapter(private var currencyList: List<Pair<String, CurrencyDetails>>) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CurrencyItemDetailsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyCode: String, currencyDetails: CurrencyDetails) {
            binding.currencyNameTextView.text = currencyCode
            binding.startRateTextView.text = "Start Rate: ${currencyDetails.start_rate}"
            binding.endRateTextView.text = "End Rate: ${currencyDetails.end_rate}"
            binding.changeTextView.text = "Change: ${currencyDetails.change}"
            binding.changePctTextView.text = "Change %: ${currencyDetails.change_pct}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CurrencyItemDetailsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (currencyCode, currencyDetails) = currencyList[position]
        holder.bind(currencyCode, currencyDetails)
    }

    override fun getItemCount() = currencyList.size

    fun updateData(newCurrencyList: List<Pair<String, CurrencyDetails>>) {
        currencyList = newCurrencyList
        notifyDataSetChanged()
    }
}
