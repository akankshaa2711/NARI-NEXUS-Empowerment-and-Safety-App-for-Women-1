package com.example.narinexus

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MoodGraphActivity : AppCompatActivity() {

    private lateinit var chart: com.github.mikephil.charting.charts.LineChart
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance() // optional
    private lateinit var tvDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_graph)

        chart = findViewById(R.id.lineChart)
        tvDetails = findViewById(R.id.tvDetails)

        setupChart()

        findViewById<ImageButton>(R.id.btnAddMood).setOnClickListener {
            showAddMoodDialog()
        }

        findViewById<ImageButton>(R.id.btnRefresh).setOnClickListener {
            loadAndPlot()
        }

        // initial plot
        loadAndPlot()
    }

    private fun setupChart() {
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        val desc = Description()
        desc.text = ""
        chart.description = desc
        chart.axisRight.isEnabled = false
        val x = chart.xAxis
        x.granularity = 1f
        chart.invalidate()
    }

    // Load mood entries from Firestore and plot them.
    private fun loadAndPlot() {
        val uid = auth.currentUser?.uid ?: "anonymous" // change logic if using real auth

        db.collection("users").document(uid).collection("mood_entries")
            .orderBy("entryDate") // stored as timestamp/long
            .get()
            .addOnSuccessListener { snapshot ->
                val entries = ArrayList<Entry>()
                val labels = ArrayList<String>()
                var detailsText = StringBuilder()
                for (doc in snapshot.documents) {
                    val mood = (doc.getLong("mood") ?: 0L).toFloat()
                    val entryDateMillis = (doc.getLong("entryDate") ?: 0L)
                    val periodStartMillis = (doc.getLong("periodStart") ?: 0L)

                    // compute cycle day safely (integer days)
                    val cycleDay = if (periodStartMillis > 0 && entryDateMillis > 0) {
                        val diff = entryDateMillis - periodStartMillis
                        val days = (diff / (1000L * 60L * 60L * 24L)).toInt()
                        days
                    } else {
                        0
                    }

                    // Use cycleDay as X; if negative or huge it may still plot - that's fine
                    entries.add(Entry(cycleDay.toFloat(), mood))
                    val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
                    labels.add(sdf.format(Date(entryDateMillis)))
                    val note = doc.getString("note") ?: ""
                    detailsText.append("Day $cycleDay → Mood $mood (${sdf.format(Date(entryDateMillis))}) ${if(note.isNotEmpty()) "- $note" else ""}\n")
                }

                // Sort entries by X (cycleDay) to make a nicer line
                entries.sortBy { it.x }

                if (entries.isEmpty()) {
                    tvDetails.text = "No mood entries yet. Tap + to add."
                    chart.clear()
                    chart.invalidate()
                    return@addOnSuccessListener
                }

                val ds = LineDataSet(entries, "Mood (1–5)")
                ds.lineWidth = 2f
                ds.circleRadius = 4f
                ds.setDrawValues(false)
                ds.mode = LineDataSet.Mode.CUBIC_BEZIER

                val data = LineData(ds)
                chart.data = data
                chart.invalidate()

                tvDetails.text = detailsText.toString()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error loading mood entries", Toast.LENGTH_SHORT).show()
            }
    }

    // Show dialog to add a mood entry
    private fun showAddMoodDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_mood, null)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val etEntryDate = view.findViewById<EditText>(R.id.etEntryDate)
        val etPeriodStart = view.findViewById<EditText>(R.id.etPeriodStart)

        // Defensive: ensure rating bar accepts input & integer steps
        ratingBar.stepSize = 1.0f
        ratingBar.numStars = 5
        ratingBar.rating = 0f

        // Optional: color the star progress programmatically (makes selected stars visible)
        try {
            // Use progressTintList when available
            val purple = android.graphics.Color.parseColor("#6A1B9A") // change as desired
            ratingBar.progressTintList = android.content.res.ColorStateList.valueOf(purple)
            ratingBar.secondaryProgressTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#D1B3E6")
            )
        } catch (e: Exception) {
            // ignore if device/theme doesn't support tint calls
            e.printStackTrace()
        }

        // Optional: immediate feedback when user taps stars
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    // Example: toast (comment out to avoid annoyance)
                    // Toast.makeText(this, "Selected $rating", Toast.LENGTH_SHORT).show()
                }
            }

        val cal = Calendar.getInstance()

        fun bindDateField(editText: EditText) {
            editText.setOnClickListener {
                val dp = DatePickerDialog(this, { _, y, m, d ->
                    val selCal = Calendar.getInstance()
                    selCal.set(y, m, d)
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    editText.setText(sdf.format(selCal.time))
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                dp.show()
            }
        }
        bindDateField(etEntryDate)
        bindDateField(etPeriodStart)

        // default entry date to today
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        etEntryDate.setText(sdf.format(Date()))

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add Mood Entry")
            .setView(view)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val btn = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            btn.setOnClickListener {
                val mood = ratingBar.rating.toInt()
                val note = etNote.text.toString().trim()
                val entryDateStr = etEntryDate.text.toString().trim()
                val periodStartStr = etPeriodStart.text.toString().trim()

                if (mood <= 0) {
                    Toast.makeText(this, "Please choose a mood (1-5)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val sdfParse = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val entryMillis = try {
                    sdfParse.parse(entryDateStr)?.time ?: System.currentTimeMillis()
                } catch (e: Exception) {
                    System.currentTimeMillis()
                }
                val periodMillis = try {
                    if (periodStartStr.isNotEmpty())
                        sdfParse.parse(periodStartStr)?.time ?: 0L
                    else 0L
                } catch (e: Exception) {
                    0L
                }

                saveMoodToFirestore(mood, note, entryMillis, periodMillis)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun saveMoodToFirestore(mood: Int, note: String, entryMillis: Long, periodMillis: Long) {
        val uid = auth.currentUser?.uid ?: "anonymous"
        val data = hashMapOf(
            "mood" to mood,
            "note" to note,
            "entryDate" to entryMillis,
            "periodStart" to periodMillis,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users").document(uid).collection("mood_entries")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Mood saved", Toast.LENGTH_SHORT).show()
                loadAndPlot()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error saving mood", Toast.LENGTH_SHORT).show()
            }
    }
}
