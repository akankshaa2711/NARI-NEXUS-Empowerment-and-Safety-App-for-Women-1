package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class womenEntrepreneurs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_women_entrepreneurs)

        val bookProduct1Button: ImageButton = findViewById(R.id.bookProduct1Button)
        bookProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=She+Means+Business"))
            startActivity(intent)
        }
        val bookProduct2Button: ImageButton = findViewById(R.id.bookProduct2Button)
        bookProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Girl+Boss+book"))
            startActivity(intent)
        }
        val bookProduct3Button: ImageButton = findViewById(R.id.bookProduct3Button)
        bookProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Lean+In+book"))
            startActivity(intent)
        }
        val bookProduct4Button: ImageButton = findViewById(R.id.bookProduct4Button)
        bookProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Women+Who+Launch+book"))
            startActivity(intent)
        }
    }
}