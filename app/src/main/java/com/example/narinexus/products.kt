package com.example.narinexus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class products : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)

        val pads = findViewById<ImageButton>(R.id.padsButton)
        pads.setOnClickListener {
            val i = Intent(this, sanitaryPads::class.java)
            startActivity(i)
        }
        val medi = findViewById<ImageButton>(R.id.medicineButton)
        medi.setOnClickListener {
            val j = Intent(this, Medicines::class.java)
            startActivity(j)
        }
        val pE = findViewById<ImageButton>(R.id.pregnancyButton)
        pE.setOnClickListener {
            val k = Intent(this, pregnancyEssentials::class.java)
            startActivity(k)
        }
        val mental = findViewById<ImageButton>(R.id.mentalWellnessButton)
        mental.setOnClickListener {
            val l = Intent(this, mentalWellness::class.java)
            startActivity(l)
        }
        val home = findViewById<ImageButton>(R.id.homeSafetyButton)
        home.setOnClickListener {
            val m = Intent(this, homeSafety::class.java)
            startActivity(m)
        }
        val selfCare = findViewById<ImageButton>(R.id.selfcareButton)
        selfCare.setOnClickListener {
            val n = Intent(this, selfcareProducts::class.java)
            startActivity(n)
        }
        val fit = findViewById<ImageButton>(R.id.fitnessButton)
        fit.setOnClickListener {
            val o = Intent(this, fitness::class.java)
            startActivity(o)
        }
        val pdress = findViewById<ImageButton>(R.id.pregnancyDressButton)
        pdress.setOnClickListener {
            val p = Intent(this, pregnancyDress::class.java)
            startActivity(p)
        }
        val idress = findViewById<ImageButton>(R.id.indianDressButton)
        idress.setOnClickListener {
            val q = Intent(this, indianDress::class.java)
            startActivity(q)
        }
        val jwellary = findViewById<ImageButton>(R.id.jewelleryButton)
        jwellary.setOnClickListener {
            val r = Intent(this, jewellery::class.java)
            startActivity(r)
        }
        val wmn = findViewById<ImageButton>(R.id.womenEntrepreneursButton)
        wmn.setOnClickListener {
            val i = Intent(this, womenEntrepreneurs::class.java)
            startActivity(i)
        }
        val wdress = findViewById<ImageButton>(R.id.westernDressButton)
        wdress.setOnClickListener {
            val s = Intent(this, westrenDress::class.java)
            startActivity(s)
        }
    }
}