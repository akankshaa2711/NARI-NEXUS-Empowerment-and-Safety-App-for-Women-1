package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
class visinory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visinory)

        findViewById<ImageButton>(R.id.register_seminar1).setOnClickListener {
            openWebsite("https://www.eventbrite.com/e/women-in-leadership-summit-tickets-1234567890")
        }

        findViewById<ImageButton>(R.id.register_seminar2).setOnClickListener {
            openWebsite("https://www.ticketswala.com/events/empowering-women-tech-2025")
        }

        findViewById<ImageButton>(R.id.register_seminar3).setOnClickListener {
            openWebsite("https://www.ficci.in/events-page.asp?evid=25923") // Women Entrepreneurs Success
        }

        findViewById<ImageButton>(R.id.register_seminar4).setOnClickListener {
            openWebsite("https://nasscom.in/event/women-stem-conclave")
        }

        findViewById<ImageButton>(R.id.register_seminar5).setOnClickListener {
            openWebsite("https://www.youtube.com/watch?v=4IWj9kJv9IQ") // Women's Health & Wellness Podcast
        }

        findViewById<ImageButton>(R.id.register_seminar6).setOnClickListener {
            openWebsite("https://www.youtube.com/watch?v=3k0OxwzCZ-U") // Women in Arts: Creative Journeys
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}