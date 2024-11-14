package com.example.k_realtime.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.k_realtime.R


class MainActivity : AppCompatActivity() {

    private lateinit var BtnNextPage: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BtnNextPage = findViewById(R.id.NextPage)

        BtnNextPage.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
        }

    }
}