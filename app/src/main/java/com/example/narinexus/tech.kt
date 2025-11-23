package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class tech : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech)

        val btnEvent1 = findViewById<Button>(R.id.register_event1)
        val btnEvent2 = findViewById<Button>(R.id.register_event2)
        val btnEvent3 = findViewById<Button>(R.id.register_event3)
        val btnEvent4 = findViewById<Button>(R.id.register_event4)

        btnEvent1.setOnClickListener {
            val url = "https://www.eventbrite.com/e/machine-learning-summit-2025-applied-ml-engineering-to-genai-and-llms-tickets-1332848338259"
            openRegistrationLink(url)
        }

        btnEvent2.setOnClickListener {
            val url = "https://cloud.withgoogle.com/workshops"
            openRegistrationLink(url)
        }

        btnEvent3.setOnClickListener {
            val url = "https://community.databricks.com/t5/events/dais-2025-virtual-learning-festival-11-june-02-july-2025/ev-p/119323"
            openRegistrationLink(url)
        }

        btnEvent4.setOnClickListener {
            val url = "https://www.databricks.com/dataaisummit"
            openRegistrationLink(url)
        }
    }

    private fun openRegistrationLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}