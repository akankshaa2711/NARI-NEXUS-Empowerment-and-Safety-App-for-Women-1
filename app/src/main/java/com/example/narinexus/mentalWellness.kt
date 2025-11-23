package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class mentalWellness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mental_wellness)

        val mentalWellnessProduct1Button: ImageButton = findViewById(R.id.mentalWellnessProduct1Button)
        mentalWellnessProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=mindfulness+book+for+women"))
            startActivity(intent)
        }

        val mentalWellnessProduct2Button: ImageButton = findViewById(R.id.mentalWellnessProduct2Button)
        mentalWellnessProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/anxiety-relief-books/s?k=anxiety+relief+books"))
            startActivity(intent)
        }

        val mentalWellnessProduct3Button: ImageButton = findViewById(R.id.mentalWellnessProduct3Button)
        mentalWellnessProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=therapy+journal+for+women"))
            startActivity(intent)
        }

        val mentalWellnessProduct4Button: ImageButton = findViewById(R.id.mentalWellnessProduct4Button)
        mentalWellnessProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=gratitude+diary+for+women"))
            startActivity(intent)
        }
    }
}