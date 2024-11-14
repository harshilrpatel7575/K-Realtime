package com.example.k_realtime.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.k_realtime.data.repository.CurrencyRepository

class ConverterViewModelFactory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConverterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
