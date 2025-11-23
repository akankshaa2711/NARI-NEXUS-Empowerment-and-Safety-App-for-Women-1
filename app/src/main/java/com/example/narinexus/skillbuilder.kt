package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class skillbuilder :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skillbuilder)

        findViewById<Button>(R.id.register_android_dev).setOnClickListener {
            openWebsite("https://forms.gle/AFm8FCMPmhRoATw99")
        }

        findViewById<Button>(R.id.register_kotlin).setOnClickListener {
            openWebsite("https://forms.gle/qShyKJLbc3WZjwPy7")
        }

        findViewById<Button>(R.id.register_public_speaking).setOnClickListener {
            openWebsite("https://forms.gle/gYuNUmGWcRJscuPe6")
        }

        findViewById<Button>(R.id.register_public_speaking_offline).setOnClickListener {
            openWebsite("https://forms.gle/m2SHzP8MsccBPknbA")
        }

        findViewById<Button>(R.id.register_mandala).setOnClickListener {
            openWebsite("https://forms.gle/PXfZBz97rVLoq2Yx5")
        }

        findViewById<Button>(R.id.register_watercolor).setOnClickListener {
            openWebsite("https://forms.gle/EPBMrAYiSFWsRZDH6")
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}