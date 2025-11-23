package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class fitness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fitness)

        val fitnessProduct1Button: ImageButton = findViewById(R.id.fitnessProduct1Button)
        fitnessProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Yoga+Mat"))
            startActivity(intent)
        }
        val fitnessProduct2Button: ImageButton = findViewById(R.id.fitnessProduct2Button)
        fitnessProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Dumbbells"))
            startActivity(intent)
        }
        val fitnessProduct3Button: ImageButton = findViewById(R.id.fitnessProduct3Button)
        fitnessProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Resistance+Bands"))
            startActivity(intent)
        }
        val fitnessProduct4Button: ImageButton = findViewById(R.id.fitnessProduct4Button)
        fitnessProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Skipping+Rope"))
            startActivity(intent)
        }
    }
}