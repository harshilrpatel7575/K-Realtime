package com.example.k_realtime.data.model

data class CurrencyResponse(
    val rates: Map<String, Map<String, String>>
)