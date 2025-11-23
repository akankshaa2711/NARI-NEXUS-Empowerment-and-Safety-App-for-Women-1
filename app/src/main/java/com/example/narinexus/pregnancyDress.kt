package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pregnancyDress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregnancy_dress)

        val pregnancyDressProduct1Button: ImageButton = findViewById(R.id.pregnancyDressProduct1Button)
        pregnancyDressProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Floral%20Maternity%20Dress%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val pregnancyDressProduct2Button: ImageButton = findViewById(R.id.pregnancyDressProduct2Button)
        pregnancyDressProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Feeding%20Gown%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val pregnancyDressProduct3Button: ImageButton = findViewById(R.id.pregnancyDressProduct3Button)
        pregnancyDressProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Maternity%20Kurta%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val pregnancyDressProduct4Button: ImageButton = findViewById(R.id.pregnancyDressProduct4Button)
        pregnancyDressProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Nursing%20Nightwear%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
    }
}