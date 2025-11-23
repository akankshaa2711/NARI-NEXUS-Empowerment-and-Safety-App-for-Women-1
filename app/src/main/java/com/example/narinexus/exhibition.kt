package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class exhibition :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition)

        findViewById<Button>(R.id.register_exhibition1).setOnClickListener {
            openWebsite("https://www.vtexpo.com") // Virtual Tech Expo
        }

        findViewById<Button>(R.id.register_exhibition2).setOnClickListener {
            openWebsite("https://www.digitalartfair.io") // Digital Art Gallery
        }

        findViewById<Button>(R.id.register_exhibition3).setOnClickListener {
            openWebsite("https://www.innofair.org") // Tech Innovation Fair
        }

        findViewById<Button>(R.id.register_exhibition4).setOnClickListener {
            openWebsite("https://www.startupshowcase.live") // Startup Showcase
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}