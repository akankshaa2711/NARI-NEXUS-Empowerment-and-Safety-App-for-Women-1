package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Medicines : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_medicines)

        val medicine1Button: ImageButton = findViewById(R.id.medicine1Button)
        medicine1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Iron+%2F+Folic+Acid+Tablets+for+women"))
            startActivity(intent)
        }

        val medicine2Button: ImageButton = findViewById(R.id.medicine2Button)
        medicine2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Calcium+Supplements+for+women"))
            startActivity(intent)
        }

        val medicine3Button: ImageButton = findViewById(R.id.medicine3Button)
        medicine3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=Multivitamins+for+women"))
            startActivity(intent)
        }

        val medicine4Button: ImageButton = findViewById(R.id.medicine4Button)
        medicine4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?k=PCOS%2FPCOD+Supplements+for+women"))
            startActivity(intent)
        }
    }
}