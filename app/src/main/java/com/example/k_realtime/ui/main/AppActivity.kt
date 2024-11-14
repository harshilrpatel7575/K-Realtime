package com.example.k_realtime.ui.main

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.k_realtime.R
import com.example.k_realtime.data.repository.CurrencyRepository
import com.example.k_realtime.network.ApiService
import com.example.k_realtime.ui.converter.ConverterViewModel
import com.example.k_realtime.ui.converter.ConverterViewModelFactory
import com.example.k_realtime.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppActivity : AppCompatActivity() {

    private lateinit var spinnerBaseCurrency: Spinner
    private lateinit var spinnerTargetCurrency: Spinner
    private lateinit var etAmount: EditText
    private lateinit var btnConvert: Button
    private lateinit var tvResult: TextView

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val repository by lazy { CurrencyRepository(apiService) }
    private val viewModel: ConverterViewModel by viewModels { ConverterViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        spinnerBaseCurrency = findViewById(R.id.spinnerBaseCurrency)
        spinnerTargetCurrency = findViewById(R.id.spinnerTargetCurrency)
        etAmount = findViewById(R.id.etAmount)
        btnConvert = findViewById(R.id.btnConvert)
        tvResult = findViewById(R.id.tvResult)

        val currencies = arrayOf("ZAR", "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerBaseCurrency.adapter = adapter
        spinnerTargetCurrency.adapter = adapter

        btnConvert.setOnClickListener {
            val baseCurrency = spinnerBaseCurrency.selectedItem.toString().trim()
            val targetCurrency = spinnerTargetCurrency.selectedItem.toString().trim()
            val amount = etAmount.text.toString().trim()

            if (baseCurrency.isNotEmpty() && targetCurrency.isNotEmpty() && amount.isNotEmpty()) {
                viewModel.convertCurrency(Constants.API_KEY, baseCurrency, targetCurrency, amount)
            } else {
                tvResult.text = "Please fill in all fields"
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversionResult.collect { result ->
                tvResult.text = result
            }
        }
    }
}
