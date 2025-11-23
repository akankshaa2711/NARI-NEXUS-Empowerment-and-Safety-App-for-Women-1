package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class helplineNumbers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_helpline_numbers)

        // Set onClickListeners
        findViewById<Button>(R.id.callWomenHelpline).setOnClickListener {
            dialNumber("1091")
        }
        findViewById<Button>(R.id.callDomesticViolence).setOnClickListener {
            dialNumber("181")
        }
        findViewById<Button>(R.id.callNCW).setOnClickListener {
            dialNumber("01126942369")
        }
        findViewById<Button>(R.id.callPolice).setOnClickListener {
            dialNumber("100")
        }
        findViewById<Button>(R.id.callAmbulance).setOnClickListener {
            dialNumber("102")
        }
        findViewById<Button>(R.id.callMentalHealth).setOnClickListener {
            dialNumber("08046110007")
        }
        findViewById<Button>(R.id.callChildHelpline).setOnClickListener {
            dialNumber("1098")
        }
    }
    private fun dialNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
}