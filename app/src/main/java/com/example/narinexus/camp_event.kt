package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class camp_event : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camp_event)

        findViewById<Button>(R.id.register_camp1).setOnClickListener {
            openWebsite("https://www.akanksha.org")
        }

        findViewById<Button>(R.id.register_camp2).setOnClickListener {
            openWebsite("https://www.sangath.in")
        }

        findViewById<Button>(R.id.register_camp3).setOnClickListener {
            openWebsite("https://www.icanfoundation.org")
        }

        findViewById<Button>(R.id.register_camp4).setOnClickListener {
            openWebsite("https://www.artofliving.org")
        }

        findViewById<Button>(R.id.register_camp5).setOnClickListener {
            openWebsite("https://www.pratham.org")
        }

        findViewById<Button>(R.id.register_camp6).setOnClickListener {
            openWebsite("https://www.teachforindia.org")
        }

        findViewById<Button>(R.id.register_camp7).setOnClickListener {
            openWebsite("https://www.sevabharati.org")
        }

        findViewById<Button>(R.id.register_camp8).setOnClickListener {
            openWebsite("https://goonj.org")
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
