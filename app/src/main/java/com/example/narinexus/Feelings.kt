package com.example.narinexus

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class FeelingsActivity : AppCompatActivity() {

    private lateinit var etFeelingFull: EditText
    private lateinit var btnMic: ImageButton
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var speechLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feelings)

        etFeelingFull = findViewById(R.id.etFeelingFull)
        btnMic = findViewById(R.id.btnFeelingMic)
        btnSave = findViewById(R.id.btnSaveFeeling)
        btnCancel = findViewById(R.id.btnCancelFeeling)

        // Pre-fill if text passed in
        val prefill = intent.getStringExtra("prefill_feeling")
        if (!prefill.isNullOrEmpty()) {
            etFeelingFull.setText(prefill)
            etFeelingFull.setSelection(etFeelingFull.text?.length ?: 0)
        }

        // Register speech launcher
        speechLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val matches = result.data!!.getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS)
                if (!matches.isNullOrEmpty()) {
                    val recognized = matches.joinToString(" ")
                    val current = etFeelingFull.text?.toString() ?: ""
                    etFeelingFull.setText((current + " " + recognized).trim())
                    etFeelingFull.setSelection(etFeelingFull.text?.length ?: 0)
                }
            }
        }

        btnMic.setOnClickListener {
            // Check mic permission
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO), 1500)
            } else {
                launchSpeech()
            }
        }

        btnSave.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("feeling_text", etFeelingFull.text?.toString() ?: "")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun launchSpeech() {
        val intent = Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "Speak your feelings")
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Speech not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    // if mic permission granted, user needs to press mic again to start speech; this is fine UX
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1500) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Microphone permission granted. Tap mic again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Microphone permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
