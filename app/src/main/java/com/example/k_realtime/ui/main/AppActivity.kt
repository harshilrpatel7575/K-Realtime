package com.example.k_realtime.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.k_realtime.databinding.ActivityAppBinding
import com.example.k_realtime.network.RetrofitInstance
import com.example.k_realtime.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        val currencies = arrayOf("ZAR", "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerBaseCurrency.adapter = adapter
        binding.spinnerTargetCurrency.adapter = adapter

        binding.btnConvert.setOnClickListener {
            val baseCurrency = binding.spinnerBaseCurrency.selectedItem.toString().trim()
            val targetCurrency = binding.spinnerTargetCurrency.selectedItem.toString().trim()
            val amount = binding.etAmount.text.toString().trim()

            if (baseCurrency.isNotEmpty() && targetCurrency.isNotEmpty() && amount.isNotEmpty()) {
                hideKeyboard()
                convertCurrency(baseCurrency, targetCurrency, amount)
            } else {
                binding.tvResult.text = "Please fill in all fields"
            }
        }
        binding.btnCopy.setOnClickListener {
            copyToClipboard(binding.tvResult.text.toString())
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun convertCurrency(baseCurrency: String, targetCurrency: String, amount: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.apiService.convertCurrency(Constants.API_KEY, baseCurrency, targetCurrency, amount)
                }
                val targetRate = response.rates[targetCurrency]?.get("rate_for_amount") ?: "N/A"
                binding.tvResult.text = "$targetRate $targetCurrency"
            } catch (e: Exception) {
                binding.tvResult.text = "Conversion failed: ${e.message}"
            }
        }
    }

    private fun copyToClipboard(text: String) {
        if (text.isNotEmpty()) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Converted Result", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show()
        }
    }
}