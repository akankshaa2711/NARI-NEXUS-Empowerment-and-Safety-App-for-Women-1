package com.example.narinexus

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class selfcareProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selfcare_products)

        val selfCareProduct1Button: ImageButton = findViewById(R.id.selfCareProduct1Button)
        selfCareProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Face+Mask"))
            startActivity(intent)
        }
        val selfCareProduct2Button: ImageButton = findViewById(R.id.selfCareProduct2Button)
        selfCareProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Body+Scrub"))
            startActivity(intent)
        }
        val selfCareProduct3Button: ImageButton = findViewById(R.id.selfCareProduct3Button)
        selfCareProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Hair+Oil"))
            startActivity(intent)
        }
        val selfCareProduct4Button: ImageButton = findViewById(R.id.selfCareProduct4Button)
        selfCareProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Moisturizer"))
            startActivity(intent)
        }
    }
}