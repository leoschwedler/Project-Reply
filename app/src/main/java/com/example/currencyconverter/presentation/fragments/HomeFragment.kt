import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.dto.CurrenciesResponse
import com.example.currencyconverter.data.dto.CurrencyConversionResponse
import com.example.currencyconverter.data.remote.RetrofitHelper
import com.example.currencyconverter.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val exchangerate by lazy {
        RetrofitHelper.exchangeRateAPI
    }
    private var currencyList: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            currencies()
        }

        binding.button6.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                convertCurrency()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun convertCurrency() {
        var retorno: Response<CurrencyConversionResponse>? = null
        val from = binding.spinner.selectedItem.toString()
        val to = binding.spinner2.selectedItem.toString()
        val amount = binding.editTextAmout.text.toString().toDouble()

        try {
            retorno = exchangerate.currencyConversion(from, to, amount)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_api", "Erro ao fazer a conversao ${e.message}")
        }
        if (retorno != null) {
            if (retorno.isSuccessful) {
                val currencyConversionResponse = retorno.body()
                val result = currencyConversionResponse?.result
                val quote = currencyConversionResponse?.info?.quote
                withContext(Dispatchers.Main) {
                    binding.textViewResult.text = result.toString()
                    binding.textViewQuote.text = "1 $from = $quote"
                }
            }
        }
    }

    private suspend fun currencies() {
        var retorno: Response<CurrenciesResponse>? = null
        try {
            retorno = exchangerate.currencies()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (retorno != null) {
            if (retorno.isSuccessful) {
                val currenciesResponse = retorno.body()
                val currencies = currenciesResponse?.currencies?.keys?.toList() ?: listOf()
                withContext(Dispatchers.Main) {
                    updateSpinners(currencies)
                }
            }
        }
    }

    private fun updateSpinners(currencies: List<String>) {
        currencyList = currencies
        val adapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner, // Layout customizado para os itens do Spinner
            currencyList
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown) // Layout customizado para o dropdown

        binding.spinner.adapter = adapter
        binding.spinner2.adapter = adapter
    }
}
