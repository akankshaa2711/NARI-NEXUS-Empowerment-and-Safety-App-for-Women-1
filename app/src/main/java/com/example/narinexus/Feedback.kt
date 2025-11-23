package com.example.narinexus

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FeedbackFormActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var editFields: Map<Int, EditText>
    private lateinit var micButtons: Map<Int, ImageButton>

    private lateinit var speechLauncher: ActivityResultLauncher<Intent>
    private var activeFieldResId: Int = 0

    private val RECORD_AUDIO_CODE = 1700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        // Build map of EditText resources (same ids as in your feedbackform.xml)
        editFields = mapOf(
            R.id.etOverallSatisfaction to findViewById(R.id.etOverallSatisfaction),
            R.id.etEasyToUse to findViewById(R.id.etEasyToUse),
            R.id.etExpectations to findViewById(R.id.etExpectations),
            R.id.etMostUsefulFeature to findViewById(R.id.etMostUsefulFeature),
            R.id.etMissingFeature to findViewById(R.id.etMissingFeature),
            R.id.etSpeedPerformance to findViewById(R.id.etSpeedPerformance),
            R.id.etDesignAppearance to findViewById(R.id.etDesignAppearance),
            R.id.etEasyFind to findViewById(R.id.etEasyFind),
            R.id.etRecommendApp to findViewById(R.id.etRecommendApp),
            R.id.etUsageFrequency to findViewById(R.id.etUsageFrequency),
            R.id.etImprove to findViewById(R.id.etImprove),
            R.id.etOtherSuggestions to findViewById(R.id.etOtherSuggestions)
        )

        // Map mic buttons using ids in layout
        micButtons = mapOf(
            R.id.mic_etOverallSatisfaction to findViewById(R.id.mic_etOverallSatisfaction),
            R.id.mic_etEasyToUse to findViewById(R.id.mic_etEasyToUse),
            R.id.mic_etExpectations to findViewById(R.id.mic_etExpectations),
            R.id.mic_etMostUsefulFeature to findViewById(R.id.mic_etMostUsefulFeature),
            R.id.mic_etMissingFeature to findViewById(R.id.mic_etMissingFeature),
            R.id.mic_etSpeedPerformance to findViewById(R.id.mic_etSpeedPerformance),
            R.id.mic_etDesignAppearance to findViewById(R.id.mic_etDesignAppearance),
            R.id.mic_etEasyFind to findViewById(R.id.mic_etEasyFind),
            R.id.mic_etRecommendApp to findViewById(R.id.mic_etRecommendApp),
            R.id.mic_etUsageFrequency to findViewById(R.id.mic_etUsageFrequency),
            R.id.mic_etImprove to findViewById(R.id.mic_etImprove),
            R.id.mic_etOtherSuggestions to findViewById(R.id.mic_etOtherSuggestions)
        )

        // register speech recognizer launcher
        speechLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val matches = result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!matches.isNullOrEmpty()) {
                    val recognized = matches.joinToString(" ")
                    val et = editFields[activeFieldResId]
                    et?.let {
                        val current = it.text?.toString() ?: ""
                        it.setText((current + " " + recognized).trim())
                        it.setSelection(it.text.length)
                    }
                }
            }
        }

        // set mic click listeners
        for ((micId, micBtn) in micButtons) {
            micBtn.setOnClickListener {
                // deduce which edit field this mic belongs to by replacing "mic_" prefix
                // We'll map based on our known mapping above:
                activeFieldResId = when (micId) {
                    R.id.mic_etOverallSatisfaction -> R.id.etOverallSatisfaction
                    R.id.mic_etEasyToUse -> R.id.etEasyToUse
                    R.id.mic_etExpectations -> R.id.etExpectations
                    R.id.mic_etMostUsefulFeature -> R.id.etMostUsefulFeature
                    R.id.mic_etMissingFeature -> R.id.etMissingFeature
                    R.id.mic_etSpeedPerformance -> R.id.etSpeedPerformance
                    R.id.mic_etDesignAppearance -> R.id.etDesignAppearance
                    R.id.mic_etEasyFind -> R.id.etEasyFind
                    R.id.mic_etRecommendApp -> R.id.etRecommendApp
                    R.id.mic_etUsageFrequency -> R.id.etUsageFrequency
                    R.id.mic_etImprove -> R.id.etImprove
                    R.id.mic_etOtherSuggestions -> R.id.etOtherSuggestions
                    else -> 0
                }

                if (activeFieldResId != 0) {
                    requestAudioAndLaunch()
                }
            }
        }

        // submit button
        findViewById<Button>(R.id.btnSubmitFeedbackForm).setOnClickListener {
            submitFeedback()
        }
    }

    private fun requestAudioAndLaunch() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_CODE)
            // user will need to tap mic again after granting (keeps logic simple)
        } else {
            launchSpeech()
        }
    }

    private fun launchSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        try {
            speechLauncher.launch(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "Speech not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    // permission callback
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Microphone granted — tap mic again", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitFeedback() {
        val submitBtn = findViewById<Button>(R.id.btnSubmitFeedbackForm)

        // Save current text
        val previousText = submitBtn.text
        submitBtn.isEnabled = false
        submitBtn.alpha = 0.6f
        submitBtn.text = "Sending..."

        // delay 2 seconds, then restore
        submitBtn.postDelayed({
            // Clear all fields
            for ((_, et) in editFields) {
                et.text?.clear()
            }

            // Restore button state
            submitBtn.isEnabled = true
            submitBtn.alpha = 1.0f
            submitBtn.text = previousText

            // Show your toast
            Toast.makeText(
                this,
                "Thank you for your feedback. We value your feedback and try to improve more.",
                Toast.LENGTH_LONG
            ).show()
        }, 2000) // 2000 ms = 2 seconds
    }

}
