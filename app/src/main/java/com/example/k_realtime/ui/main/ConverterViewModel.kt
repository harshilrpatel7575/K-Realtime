package com.example.k_realtime.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.k_realtime.data.model.CurrencyResponse
import com.example.k_realtime.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConverterViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _conversionResult = MutableStateFlow<String>("")
    val conversionResult: StateFlow<String> get() = _conversionResult

    fun convertCurrency(apiKey: String, from: String, to: String, amount: String) {
        viewModelScope.launch {
            try {
                val response: CurrencyResponse = repository.convertCurrency(apiKey, from, to, amount)
                val targetRate = response.rates[to]?.rate_for_amount ?: "N/A"
                _conversionResult.value = "$targetRate $to"
            } catch (e: Exception) {
                _conversionResult.value = "Conversion failed: ${e.message}"
            }
        }
    }
}
