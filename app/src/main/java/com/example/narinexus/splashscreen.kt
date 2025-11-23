package com.example.narinexus

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.narinexus.R
import com.example.narinexus.MainActivity

class Splashscreen : AppCompatActivity() {
    private lateinit var text: ImageView
    private lateinit var logo: ImageView

    private var bgPlayer: MediaPlayer? = null
    private var voicePlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        text = findViewById(R.id.text)
        logo = findViewById(R.id.logo)

        // Start invisible
        text.alpha = 0f
        logo.alpha = 0f

        // Start both audios after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            // Background music - trimmed manually via handler
            bgPlayer = MediaPlayer.create(this, R.raw.bg)
            bgPlayer?.start()

            // Stop bgPlayer after 6 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                bgPlayer?.stop()
            }, 6000)

            // Voice: Welcome to Nari Nexus
            voicePlayer = MediaPlayer.create(this, R.raw.welcome_to_nari_nexus)
            voicePlayer?.start()
        }, 1000)

        // Fade-in Text
        val fadeInText = AnimationUtils.loadAnimation(this, R.anim.fade_in_text)
        text.startAnimation(fadeInText)
        text.alpha = 1f

        // Fade-in Logo
        val fadeInLogo = AnimationUtils.loadAnimation(this, R.anim.fade_in_logo)
        logo.startAnimation(fadeInLogo)
        logo.alpha = 0.6f

        // Fade-out both views after a delay
        logo.postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_slow)
            text.startAnimation(fadeOut)
            logo.startAnimation(fadeOut)

            // Stop only voice, bg already stopped after 6s
            voicePlayer?.stop()

            // Go to MainActivity after fade-out
            logo.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        bgPlayer?.release()
        voicePlayer?.release()
    }
}