package com.example.narinexus


import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class discover_more :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_more)

        findViewById<ImageButton>(R.id.btnCamp).setOnClickListener {
            Toast.makeText(this, "InspireCamps Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, camp_event::class.java))
        }

        findViewById<ImageButton>(R.id.btnWorkshop).setOnClickListener {
            Toast.makeText(this, "SkillBuilders Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, skillbuilder::class.java))
        }

        findViewById<ImageButton>(R.id.btnSeminar).setOnClickListener {
            Toast.makeText(this, "Visionary Talks Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, visinory::class.java))
        }

        findViewById<ImageButton>(R.id.btnTech).setOnClickListener {
            Toast.makeText(this, "TechThrive Events Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, tech::class.java))
        }

        findViewById<ImageButton>(R.id.btnCompetition).setOnClickListener {
            Toast.makeText(this, "CompeteX Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, competition::class.java))
        }

        findViewById<ImageButton>(R.id.btnExhibition).setOnClickListener {
            Toast.makeText(this, "ShowcaseSphere Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, exhibition::class.java))
        }
    }
}