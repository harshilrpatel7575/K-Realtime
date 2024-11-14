package com.example.k_realtime.data.repository

import com.example.k_realtime.data.model.CurrencyResponse
import com.example.k_realtime.network.ApiService

class CurrencyRepository(private val apiService: ApiService) {
    suspend fun convertCurrency(apiKey: String, from: String, to: String, amount: String): CurrencyResponse {
        return apiService.convertCurrency(apiKey, from, to, amount)
    }
}
