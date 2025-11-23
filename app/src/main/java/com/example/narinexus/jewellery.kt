package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class jewellery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jewellery)

        val jewelleryProduct1Button: ImageButton = findViewById(R.id.jewelleryProduct1Button)
        jewelleryProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Gold%20Plated%20Necklace%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val jewelleryProduct2Button: ImageButton = findViewById(R.id.jewelleryProduct2Button)
        jewelleryProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Oxidised%20Earrings%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val jewelleryProduct3Button: ImageButton = findViewById(R.id.jewelleryProduct3Button)
        jewelleryProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Bracelet%20Set%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val jewelleryProduct4Button: ImageButton = findViewById(R.id.jewelleryProduct4Button)
        jewelleryProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=Finger%20Rings%20Combo%20for%20women&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
    }
}