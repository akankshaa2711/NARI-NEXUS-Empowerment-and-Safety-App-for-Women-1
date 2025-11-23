package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sanitaryPads : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sanitary_pads)

        val product1Button: ImageButton = findViewById(R.id.product1Button)
        product1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Whisper+Ultra+Clean+XL"))
            startActivity(intent)
        }

        val product2Button: ImageButton = findViewById(R.id.product2Button)
        product2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Stayfree+Secure+XL"))
            startActivity(intent)
        }

        val product3Button: ImageButton = findViewById(R.id.product3Button)
        product3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Sofy+AntiBacteria+XL"))
            startActivity(intent)
        }

        val product4Button: ImageButton = findViewById(R.id.product4Button)
        product4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Nua+Ultra+Thin+Pads"))
            startActivity(intent)
        }
    }
}