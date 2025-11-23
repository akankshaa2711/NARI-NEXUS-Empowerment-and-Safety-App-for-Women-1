package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class pregnancyEssentials : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregnancy_essentials)

        val pregnancyProduct1Button: ImageButton = findViewById(R.id.pregnancyProduct1Button)
        pregnancyProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=prenatal+vitamins+for+women"))
            startActivity(intent)
        }

        val pregnancyProduct2Button: ImageButton = findViewById(R.id.pregnancyProduct2Button)
        pregnancyProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=stretch+marks+cream+for+women"))
            startActivity(intent)
        }

        val pregnancyProduct3Button: ImageButton = findViewById(R.id.pregnancyProduct3Button)
        pregnancyProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=pregnancy+pillow+for+women"))
            startActivity(intent)
        }

        val pregnancyProduct4Button: ImageButton = findViewById(R.id.pregnancyProduct4Button)
        pregnancyProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=maternity+belt+for+women"))
            startActivity(intent)
        }
    }
}