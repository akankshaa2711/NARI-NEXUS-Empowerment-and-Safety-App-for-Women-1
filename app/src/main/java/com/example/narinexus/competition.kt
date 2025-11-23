package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class competition :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competition)

        findViewById<Button>(R.id.register_competition1).setOnClickListener {
            openWebsite("https://hack2skill.com/hack/coderush") // Coderush Hackathon
        }

        findViewById<Button>(R.id.register_competition2).setOnClickListener {
            openWebsite("https://www.debate.org/register") // Debate Challenge
        }

        findViewById<Button>(R.id.register_competition3).setOnClickListener {
            openWebsite("https://robogenius.com/robotics-competition") // Robotics Challenge
        }

        findViewById<Button>(R.id.register_competition4).setOnClickListener {
            openWebsite("https://www.quizizz.com/join") // Quiz Mania
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}