package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UshaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usha)

//        val knowMoreBtn = findViewById<Button>(R.id.knowMoreUsha)
//
//        knowMoreBtn.setOnClickListener {
//            val url = "https://www.thebetterindia.com/201454/uttar-pradesh-red-brigade-rape-self-defence-empowerment-lucknow-hero-asha-india/"
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse(url)
//            startActivity(intent)
//        }
    }
}