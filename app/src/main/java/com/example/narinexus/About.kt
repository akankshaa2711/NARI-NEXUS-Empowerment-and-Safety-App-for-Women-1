package com.example.narinexus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Optional: you could wire profile image clicks to pick real images later.
        val p1 = findViewById<ImageView>(R.id.dev1_img)
        val p2 = findViewById<ImageView>(R.id.dev2_img)
        val p3 = findViewById<ImageView>(R.id.dev3_img)
        val p4 = findViewById<ImageView>(R.id.dev4_img)

        val toast = { name: String ->
            Toast.makeText(this, "$name — Photo will be Uploaded shortly.", Toast.LENGTH_SHORT).show()
        }

        p1.setOnClickListener { toast("Anchal Shukla") }
        p2.setOnClickListener { toast("Yash Vishwakarma") }
        p3.setOnClickListener { toast("Abhishek patel") }
        p4.setOnClickListener { toast("Adarsh kamal") }
    }
}
