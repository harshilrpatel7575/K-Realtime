package com.example.k_realtime.network

import com.example.k_realtime.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/currency/convert")
    suspend fun convertCurrency(
        @Query("api_key") apiKey: String,
        @Query("from") baseCurrency: String,
        @Query("to") targetCurrency: String,
        @Query("amount") amount: String,
        @Query("format") format: String = "json"
    ): CurrencyResponse
}
