package com.example.k_realtime.data.model

data class CurrencyResponse(
    val status: String,
    val rates: Map<String, Rate>
)

data class Rate(
    val rate_for_amount: String
)
