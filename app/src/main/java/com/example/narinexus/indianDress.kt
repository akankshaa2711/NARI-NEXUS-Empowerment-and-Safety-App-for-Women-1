package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class indianDress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_indian_dress)

        val indianDressProduct1Button: ImageButton = findViewById(R.id.indianDressProduct1Button)
        indianDressProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Anarkali%20Suit%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val indianDressProduct2Button: ImageButton = findViewById(R.id.indianDressProduct2Button)
        indianDressProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Saree%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val indianDressProduct3Button: ImageButton = findViewById(R.id.indianDressProduct3Button)
        indianDressProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Lehenga%20Choli%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=off&as=off"))
            startActivity(intent)
        }
        val indianDressProduct4Button: ImageButton = findViewById(R.id.indianDressProduct4Button)
        indianDressProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Kurti%20Set%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
    }
}