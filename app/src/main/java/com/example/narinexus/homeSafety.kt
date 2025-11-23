package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class homeSafety : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_safety)

        val homeSafetyProduct1Button: ImageButton = findViewById(R.id.homeSafetyProduct1Button)
        homeSafetyProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Fire+Extinguisher"))
            startActivity(intent)
        }
        val homeSafetyProduct2Button: ImageButton = findViewById(R.id.homeSafetyProduct2Button)
        homeSafetyProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Smoke+Detector"))
            startActivity(intent)
        }
        val homeSafetyProduct3Button: ImageButton = findViewById(R.id.homeSafetyProduct3Button)
        homeSafetyProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=First+Aid+Kit"))
            startActivity(intent)
        }
        val homeSafetyProduct4Button: ImageButton = findViewById(R.id.homeSafetyProduct4Button)
        homeSafetyProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Door+Security+Alarm"))
            startActivity(intent)
        }
    }
}