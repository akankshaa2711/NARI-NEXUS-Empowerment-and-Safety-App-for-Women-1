package com.example.narinexus

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.*
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Game : AppCompatActivity() {

    private lateinit var gameRoot: FrameLayout
    private var score = 0
    private var totalWords = 20
    private var wordsFallen = 0
    private var shuffledWords = mutableListOf<String>()
    private val colors = listOf("#6A1B9A", "#4A0072", "#D81B60", "#00897B", "#F57C00")

    private val words = listOf(
        "Strong", "Calm", "Confident", "Resilient", "Worthy", "Capable",
        "Loved", "Enough", "Brave", "Grateful", "Empowered", "Courageous",
        "Happy", "Kind", "Joyful", "Enthusiastic", "Patient", "Generous",
        "Optimistic", "Compassionate", "Loyal", "Friendly","Ambitious",
        "Creative", "Inspiring", "Focused", "Passionate", "Determined",
        "Hopeful", "Energetic", "Vibrant", "Honest", "Authentic", "Humble",
        "Bright", "Harmonious", "Encouraging", "Playful", "Innovative",
        "Adaptable", "Driven", "Thoughtful", "Reliable", "Mindful", "Cheerful",
        "Fearless", "Charismatic", "Appreciative", "Artistic", "Zealous", "Spirited",
        "Wise"
    )

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameRoot = findViewById(R.id.gameRoot)
        shuffledWords = words.shuffled().toMutableList()

        startGame()
    }

    private fun startGame() {
        score = 0
        wordsFallen = 0

        for (i in 1..totalWords) {
            handler.postDelayed({ dropWord() }, (i * 1500).toLong())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dropWord() {
        if (shuffledWords.isEmpty()) {
            shuffledWords = words.shuffled().toMutableList()
        }

        val randomWord = shuffledWords.removeAt(0)

        val textView = TextView(this).apply {
            text = randomWord
            textSize = 20f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor(colors.random()))
            setPadding(16, 8, 16, 8)
            isClickable = true
            setOnClickListener {
                gameRoot.removeView(this)
                score++

                // Play pop sound only on tap
                val mediaPlayer = MediaPlayer.create(context, R.raw.popup)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    it.release()
                }
            }
        }

        val size = ViewGroup.LayoutParams.WRAP_CONTENT
        val params = FrameLayout.LayoutParams(size, size)
        params.topMargin = 0
        params.leftMargin = Random.nextInt(0, gameRoot.width - 200)
        params.gravity = Gravity.TOP or Gravity.START
        textView.layoutParams = params

        gameRoot.addView(textView)
        textView.bringToFront()

        val randomDuration = Random.nextLong(1500, 4000)
        textView.animate()
            .translationY(gameRoot.height.toFloat())
            .setDuration(randomDuration)
            .withEndAction {
                gameRoot.removeView(textView)
                wordsFallen++
                if (wordsFallen == totalWords) showResult()
            }
            .start()
    }

    private fun showResult() {
        val message = when {
            score >= 15 -> "Excellent Mental Wellness! 🌟"
            score >= 10 -> "Good Job! Keep nurturing your mind. 💪"
            else -> "You may need some self-care. 💜"
        }

        AlertDialog.Builder(this)
            .setTitle("Your Mental Wellness Result")
            .setMessage("You tapped $score out of $totalWords words.\n$message")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
