package com.example.narinexus

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.*
import android.widget.*
import com.example.narinexus.R
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import android.view.animation.AnimationUtils
import androidx.core.widget.NestedScrollView
import java.util.*
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.ImageButton
import android.widget.TextView
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater

class Swasthya : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var todayDate: String
    // ---- add near other fields inside class Swasthya ----
    private lateinit var speechLauncher: androidx.activity.result.ActivityResultLauncher<Intent>
    private var lastActivatedSymptomEditText: EditText? = null
    // in class Swasthya:
    private lateinit var feelingsLauncher: androidx.activity.result.ActivityResultLauncher<Intent>
    private var currentFeelingText: String = ""
    private var currentDialogFeelingEditText: EditText? = null


    // If you want to save diary entries to Firestore (optional), keep this true and ensure firebase is configured.
    private val SAVE_TO_FIRESTORE = true

    private val tips = listOf(
        "Stay hydrated and rest well.",
        "Iron-rich food helps fatigue.",
        "Track your cycle regularly.",
        "Meditation can reduce cramps.",
        "Practice positive affirmations!"
    )
    private val motivations = listOf(
        "Your body deserves love and care.",
        "Self-care is not selfish, it's necessary.",
        "Strong, healthy, and confident — that's you!",
        "You are worthy, just as you are.",
        "The more you care for your mental health, the more you realize how unnecessary and superficial other things are.",
        "The only journey is the journey within.",
        "Self-care is how you take your power back.",
        "To enjoy the glow of good health, you must exercise.",
        "Your mental health is a priority. Your happiness is an essential. Your self-care is a necessity.",
        "The strongest people are those who win battles we know nothing about.",
        "You don’t have to control your thoughts; you just have to stop letting them control you.",
        "Mental health is not just the absence of mental illness. It’s not just the absence of pain. It’s emotional, physical, and social well-being.",
        "What mental health needs is more sunlight, more candor, and more unashamed conversation"
    )
    val factsBodyMental = listOf(
        "Women's heart disease symptoms can differ from men's — fatigue and nausea may be warning signs.",
        "Regular strength training helps prevent osteoporosis in women.",
        "Mental health disorders like depression and anxiety are more common in women.",
        "Self-exams can help detect early signs of breast cancer.",
        "Gut health affects mental well-being — a healthy diet supports both.",
        "Vitamin D deficiency is common in women and affects bone health.",
        "Staying hydrated improves skin health, digestion, and energy levels.",
        "Hormones affect sleep patterns, especially around your period or menopause.",
        "Eating enough healthy fats is essential for hormone balance.",
        "Walking just 30 minutes a day reduces the risk of chronic diseases."
        // Add more as desired
    )

    val factsMenstrual = listOf(
        "The average menstrual cycle is 28 days, but normal can range from 21 to 35 days.",
        "Stress can delay your period by affecting hormone levels.",
        "Exercise can help reduce menstrual cramps for many women.",
        "Period blood isn't just blood — it's a mix of blood, uterine tissue, and mucus.",
        "Severe period pain that disrupts daily life could be a sign of endometriosis.",
        "Tracking your period helps detect irregularities early.",
        "Some women experience 'ovulation pain' mid-cycle called Mittelschmerz.",
        "Drinking plenty of water may reduce bloating during your period.",
        "Your period may become lighter or heavier after childbirth.",
        "Using a heating pad is scientifically proven to reduce period cramps."
        // Add more as desired
    )

    val factsPregnancy = listOf(
        "You are most fertile around ovulation, typically 14 days before your next period.",
        "Folic acid before and during pregnancy reduces the risk of birth defects.",
        "A healthy BMI supports better fertility for both men and women.",
        "Smoking can reduce fertility and increase pregnancy risks.",
        "Stress can affect fertility by disrupting hormone balance.",
        "Many women don't experience pregnancy symptoms until 4-6 weeks along.",
        "Sperm can live inside the female body for up to 5 days.",
        "Excessive caffeine may reduce fertility and should be limited during pregnancy.",
        "Healthy lifestyle changes can improve chances of conceiving naturally.",
        "Age significantly impacts fertility — it declines after age 35 for women."
        // Add more as desired
    )
    val fertilityTips = listOf(
        "Track cervical mucus for better ovulation accuracy.",
        "Basal body temperature can signal ovulation.",
        "Reduce caffeine and manage stress for improved fertility.",
        "Aim for a balanced diet rich in folate and iron.",
        "Limit alcohol intake during your fertile window.",
        "Ovulation predictor kits can provide confirmation.",
        "Regular physical activity supports hormone balance.",
        "Sleep well — circadian rhythm affects fertility.",
        "Maintain a healthy BMI for better ovulation regularity."
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swasthya)
        sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        todayDate = Calendar.getInstance().let {
            "${it.get(Calendar.DAY_OF_MONTH)}/${it.get(Calendar.MONTH) + 1}/${it.get(Calendar.YEAR)}"
        }
        // register speech-to-text launcher
        speechLauncher = registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val matches = result.data!!.getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS)
                if (!matches.isNullOrEmpty()) {
                    // append recognized text to the last focused symptoms EditText
                    val recognized = matches.joinToString(" ")
                    lastActivatedSymptomEditText?.apply {
                        val current = text?.toString() ?: ""
                        setText((current + " " + recognized).trim())
                        setSelection(text?.length ?: 0)
                    }
                }
            }
        }
        // register feelings activity launcher
        feelingsLauncher = registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val returned = result.data!!.getStringExtra("feeling_text") ?: ""
                currentFeelingText = returned
                currentDialogFeelingEditText?.let { et ->
                    et.setText(returned)
                    et.setSelection(et.text?.length ?: 0)
                    // put into the short feeling EditText in the dialog if dialog opened — we will update when dialog created
                    // If dialog is open, update field directly. For simplicity, store string and fill when dialog opens.
                    // If you keep a reference to the dialog EditText, you can set it immediately.
                }
            }
        }


        requestNotificationPermission()
        createNotificationChannel()
        val flipperTips = findViewById<ViewFlipper>(R.id.viewFlipperTips)
        flipperTips.startFlipping()

        // Menstrual Wellness toggle
        val titleMenstrual = findViewById<TextView>(R.id.tvMenstrualTitle)
        val layoutMenstrual = findViewById<LinearLayout>(R.id.layoutMenstrualContent)
        titleMenstrual.setOnClickListener {
            layoutMenstrual.visibility = if (layoutMenstrual.visibility == LinearLayout.VISIBLE) LinearLayout.GONE else LinearLayout.VISIBLE
            titleMenstrual.text = if (layoutMenstrual.visibility == LinearLayout.VISIBLE)
                "Menstrual Wellness ▲" else "Menstrual Wellness ▼"

            if (layoutMenstrual.visibility == LinearLayout.VISIBLE) {
                showLastPeriodToast()
            }
        }
        findViewById<Button>(R.id.btnPMSLogger).setOnClickListener {
            showPMSDiaryDialog()
        }
        val tvFactBodyMental = findViewById<TextView>(R.id.tvFactBodyMental)
        val tvFactMenstrual = findViewById<TextView>(R.id.tvFactMenstrual)
        val tvFactPregnancy = findViewById<TextView>(R.id.tvFactPregnancy)

        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)

        tvFactBodyMental.text = factsBodyMental[dayOfYear % factsBodyMental.size]
        tvFactMenstrual.text = factsMenstrual[dayOfYear % factsMenstrual.size]
        tvFactPregnancy.text = factsPregnancy[dayOfYear % factsPregnancy.size]


        toggleSection(R.id.tvPregnancyTitle, R.id.layoutPregnancyContent)
        toggleSection(R.id.tvBodyTitle, R.id.layoutBodyContent)

        findViewById<Button>(R.id.btnPeriodTracker).setOnClickListener {
            showDatePicker()
        }
        val btnFertilityTitle = findViewById<Button>(R.id.btnFertilityTitle)
        val layoutFertilityContent = findViewById<LinearLayout>(R.id.layoutFertilityContent)
        val tvFertilityResult = findViewById<TextView>(R.id.tvFertilityResult)

        val fertilityTips = listOf(
            "Track cervical mucus to improve accuracy.",
            "Basal body temperature rises after ovulation.",
            "Eat leafy greens, folate, and zinc for reproductive health.",
            "Manage stress — cortisol affects ovulation.",
            "Sleep and hydration support hormone balance."
        )

        btnFertilityTitle.setOnClickListener {
            // Toggle visibility
            layoutFertilityContent.visibility = if (layoutFertilityContent.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            btnFertilityTitle.text = if (layoutFertilityContent.visibility == View.VISIBLE)
                "Check your Fertility window ▲" else "Check your Fertility window ▼"

            if (layoutFertilityContent.visibility == View.VISIBLE) {
                val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
                val lastDate = sharedPref.getString("lastPeriodDate", null)

                if (lastDate != null) {
                    val parts = lastDate.split("/")
                    if (parts.size == 3) {
                        try {
                            val day = parts[0].toInt()
                            val month = parts[1].toInt() - 1 // Calendar months are 0-based
                            val year = parts[2].toInt()

                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, day)

                            calendar.add(Calendar.DAY_OF_MONTH, 14)
                            val ovulationDate = calendar.time

                            val startFertile = Calendar.getInstance()
                            startFertile.time = ovulationDate
                            startFertile.add(Calendar.DAY_OF_MONTH, -5)

                            val endFertile = Calendar.getInstance()
                            endFertile.time = ovulationDate
                            endFertile.add(Calendar.DAY_OF_MONTH, 1)

                            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
                            val fertileStart = sdf.format(startFertile.time)
                            val fertileEnd = sdf.format(endFertile.time)
                            val ovulate = sdf.format(ovulationDate)
                            val randomTip = fertilityTips.random()

                            tvFertilityResult.text = """
                        🧬 Fertile Window: $fertileStart to $fertileEnd
                        💫 Ovulation Day: $ovulate
                        🌱 Tip: $randomTip
                    """.trimIndent()

                        } catch (e: Exception) {
                            tvFertilityResult.text = "⚠️ Error parsing period date."
                        }
                    } else {
                        tvFertilityResult.text = "⚠️ Invalid period date format."
                    }
                } else {
                    tvFertilityResult.text = "⚠️ Please track your last period date first."
                }
            }
        }


        findViewById<Button>(R.id.btnSelfCareReminder).setOnClickListener {
            scheduleSelfCareReminder()
        }

        findViewById<Button>(R.id.btnMentalCheckIn).setOnClickListener {
            startActivity(Intent(this, Game::class.java))
        }

        val btnDailyTips = findViewById<Button>(R.id.btnDailyTips)
        val layoutDailyTips = findViewById<LinearLayout>(R.id.layoutDailyTipsContent)
        val tvDailyTips = findViewById<TextView>(R.id.tvDailyTips)
        val scrollView = findViewById<NestedScrollView>(R.id.scrollViewSwasthya)

        updateDailyTipBasedOnCycle(tvDailyTips)

        btnDailyTips.setOnClickListener {
            if (layoutDailyTips.visibility == LinearLayout.VISIBLE) {
                layoutDailyTips.visibility = LinearLayout.GONE
                btnDailyTips.text = "Daily Tips ▼"
            } else {
                layoutDailyTips.visibility = LinearLayout.VISIBLE
                btnDailyTips.text = "Daily Tips ▲"

                // Scroll to show the expanded tips
                scrollView.post {
                    scrollView.smoothScrollTo(0, layoutDailyTips.bottom)
                }
            }
        }


        findViewById<Button>(R.id.btnPregnancyMilestones).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_preg, null)
            val milestonesText = dialogView.findViewById<TextView>(R.id.tvMilestonesText)
            milestonesText.text = """
        
        First Trimester (Weeks 1-12):-
        • Weeks 1-4: Fertilization, implantation, early development. Heart begins to beat.
        • Weeks 5-8: Rapid development, organ formation begins.
        • Weeks 9-12: Fingers, toes, brain activity. Major organ development completes.

        Second Trimester (Weeks 13-27):-
        • Weeks 13-16: Growth continues, bones harden, baby movements may be felt.
        • Weeks 17-20: Baby gains weight, hair begins to grow.
        • Weeks 21-27: Lung development starts, noticeable belly growth.

        Third Trimester (Weeks 28-40):-
        • Weeks 28-32: Rapid weight gain, lungs mature.
        • Weeks 33-36: Almost fully developed, brain grows, Braxton Hicks contractions.
        • Weeks 37-40: Full-term baby, body prepares for labor.
    """.trimIndent()

            AlertDialog.Builder(this)
                .setTitle("Pregnancy Milestones Chart")
                .setView(dialogView)
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        val btnFetalFacts = findViewById<Button>(R.id.btnFetalFacts)
        val layoutFetalFactsContent = findViewById<LinearLayout>(R.id.layoutFetalFactsContent)
        btnFetalFacts.setOnClickListener {
            layoutFetalFactsContent.visibility = if (layoutFetalFactsContent.visibility == LinearLayout.VISIBLE) LinearLayout.GONE else LinearLayout.VISIBLE
            btnFetalFacts.text = if (layoutFetalFactsContent.visibility == LinearLayout.VISIBLE)
                "Fetal Development Facts ▲" else "Fetal Development Facts ▼"
        }

        // Fetal Facts YouTube Links
        findViewById<TextView>(R.id.btnFetalFact1).setOnClickListener { openLink("https://youtu.be/vqv_dVLdR9c?si=GvLw_Nb9a-Hk02OR") }
        findViewById<TextView>(R.id.btnFetalFact2).setOnClickListener { openLink("https://youtu.be/LNd5-mvhvUs?si=eO8TfIr8--oKatil") }
        findViewById<TextView>(R.id.btnFetalFact3).setOnClickListener { openLink("https://youtu.be/8312a32dcQc?si=45qq_V_NarbmxJ4R") }
        findViewById<TextView>(R.id.btnFetalFact4).setOnClickListener { openLink("https://youtu.be/0gAsdEUNUJY?si=lhIhvWd8ROcSBxsb") }
        findViewById<TextView>(R.id.btnFetalFact5).setOnClickListener { openLink("https://youtu.be/WH9ZJu4wRUE?si=FUAQl-th314ty6tO") }
        findViewById<TextView>(R.id.btnFetalFact6).setOnClickListener { openLink("https://youtu.be/usxM_dhEK6M?si=6c2TsmDIZD1M-7GO") }
        findViewById<TextView>(R.id.btnFetalFact7).setOnClickListener { openLink("https://youtu.be/ikcXKfUvpl8?si=BFo66ZeN0QofL-ta") }

        val btnMotivation = findViewById<Button>(R.id.btnMotivation)
        val savedMotivationDate = sharedPref.getString("motivationDate", "")
        val savedMotivation = sharedPref.getString("motivationQuote", "")

        if (savedMotivationDate == todayDate) {
            btnMotivation.text = "Get Motivation"
        } else {
            sharedPref.edit().remove("motivationQuote").remove("motivationDate").apply()
        }

        btnMotivation.setOnClickListener {
            if (savedMotivationDate == todayDate && !savedMotivation.isNullOrEmpty()) {
                btnMotivation.text = savedMotivation
            } else {
                val randomIndex = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % motivations.size
                val randomQuote = motivations[randomIndex]
                btnMotivation.text = randomQuote
                sharedPref.edit()
                    .putString("motivationDate", todayDate)
                    .putString("motivationQuote", randomQuote)
                    .apply()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btnMotivation.text = "Get Motivation"
            }, 60_000)
        }


        findViewById<Button>(R.id.btnBreathing).setOnClickListener {
            showBreathingExercise()
        }

        findViewById<Button>(R.id.btnSelfCareToggle).setOnClickListener {
            val layout = findViewById<LinearLayout>(R.id.layoutHabitContent)
            val button = findViewById<Button>(R.id.btnSelfCareToggle)
            layout.visibility = if (layout.visibility == LinearLayout.VISIBLE) LinearLayout.GONE else LinearLayout.VISIBLE
            button.text = if (layout.visibility == LinearLayout.VISIBLE)
                "Self-Care Habit Tracker ▲" else "Self-Care Habit Tracker ▼"
        }

        val checkWater = findViewById<CheckBox>(R.id.checkWater)
        val checkFood = findViewById<CheckBox>(R.id.checkFood)
        val checkExercise = findViewById<CheckBox>(R.id.checkExercise)
        val checkMovement = findViewById<CheckBox>(R.id.checkMovement)
        val checkAffirmations = findViewById<CheckBox>(R.id.checkAffirmations)
        val checkRelax = findViewById<CheckBox>(R.id.checkRelax)
        val checkEmotionalCare = findViewById<CheckBox>(R.id.checkEmotionalCare)
        val checkNatureTime = findViewById<CheckBox>(R.id.checkNatureTime)
        val checkMeditation = findViewById<CheckBox>(R.id.checkMeditation)

        val checkboxes = listOf(
            checkWater, checkFood, checkExercise, checkMovement, checkAffirmations,
            checkRelax, checkEmotionalCare, checkNatureTime, checkMeditation
        )

// Reset habit checkboxes if date has changed
        val lastHabitDate = sharedPref.getString("lastHabitDate", "")
        val todayDate = Calendar.getInstance().let {
            "${it.get(Calendar.DAY_OF_MONTH)}/${it.get(Calendar.MONTH) + 1}/${it.get(Calendar.YEAR)}"
        }

        if (lastHabitDate != todayDate) {
            for (checkbox in checkboxes) {
                checkbox.isChecked = false
                sharedPref.edit().putBoolean(checkbox.resources.getResourceEntryName(checkbox.id), false).apply()
            }
            sharedPref.edit().putString("lastHabitDate", todayDate).apply()
        }

// Load saved states
        for (checkbox in checkboxes) {
            val key = checkbox.resources.getResourceEntryName(checkbox.id)
            checkbox.isChecked = sharedPref.getBoolean(key, false)
        }

// Save state when checkbox clicked
        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { cb, isChecked ->
                sharedPref.edit().putBoolean(cb.resources.getResourceEntryName(cb.id), isChecked).apply()

                // Count how many checkboxes are checked
                val completedCount = checkboxes.count { it.isChecked }

                // Show motivational Toast based on progress
                if (completedCount == checkboxes.size) {
                    Toast.makeText(this, "Amazing! You completed all your self-care habits today 🎉", Toast.LENGTH_SHORT).show()
                } else if (completedCount >= 5) {
                    Toast.makeText(this, "Great progress! Keep going 💪", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Keep ticking off your self-care habits!", Toast.LENGTH_SHORT).show()
                }
            }

        }


// Load saved states
        checkWater.isChecked = sharedPref.getBoolean("checkWater", false)
        checkFood.isChecked = sharedPref.getBoolean("checkFood", false)
        checkExercise.isChecked = sharedPref.getBoolean("checkExercise", false)
        checkMovement.isChecked = sharedPref.getBoolean("checkMovement", false)
        checkAffirmations.isChecked = sharedPref.getBoolean("checkAffirmations", false)
        checkRelax.isChecked = sharedPref.getBoolean("checkRelax", false)
        checkEmotionalCare.isChecked = sharedPref.getBoolean("checkEmotionalCare", false)
        checkNatureTime.isChecked = sharedPref.getBoolean("checkNatureTime", false)
        checkMeditation.isChecked = sharedPref.getBoolean("checkMeditation", false)


        findViewById<Button>(R.id.btnDietChart).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_diet_chart, null)
            val dietText = dialogView.findViewById<TextView>(R.id.tvDietChartContent)
            dietText.text = """
        Week 1-4: Light, easily digestible food. Fruits, buttermilk, dry fruits in moderation.
        Week 5-8: Increase iron-rich foods - spinach, beetroot, jaggery.
        Week 9-12: Include proteins - dal, paneer, eggs (if vegetarian, soy products).
        Week 13-20: Calcium rich diet - milk, curd, sesame seeds.
        Week 21-28: Add healthy fats - nuts, ghee in moderation.
        Week 29-36: High-fiber diet - veggies, fruits to avoid constipation.
        Week 37-40: Hydrate well, small frequent meals, avoid spicy food.
    """.trimIndent()

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        checkPeriodReminder() // Check for upcoming period reminder on app start

    }


    private fun resetHabitCheckboxes() {
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val checkboxKeys = listOf(
            "checkWater", "checkFood", "checkExercise", "checkMovement",
            "checkAffirmations", "checkRelax", "checkEmotionalCare",
            "checkNatureTime", "checkMeditation"
        )

        for (key in checkboxKeys) {
            editor.putBoolean(key, false)
        }
        editor.apply()
    }


    private fun showPMSDiaryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_pms_diary, null)

        val etDate = dialogView.findViewById<EditText>(R.id.etDiaryDate)
        val etSymptoms = dialogView.findViewById<EditText>(R.id.etSymptoms)
        val etStartDate = dialogView.findViewById<EditText>(R.id.etStartDate)
        val etEndDate = dialogView.findViewById<EditText>(R.id.etEndDate)
        val etOtherInfo = dialogView.findViewById<EditText>(R.id.etOtherInfo)
        val spinnerSeverity = dialogView.findViewById<Spinner>(R.id.spinnerSeverity)
        val btnMic = dialogView.findViewById<ImageButton>(R.id.btnMic)
// short feeling field
        val etFeelingShort = dialogView.findViewById<EditText>(R.id.etFeelingShort)

        currentDialogFeelingEditText = etFeelingShort

// prefill short field if we already have text returned earlier
        if (currentFeelingText.isNotEmpty() && etFeelingShort.text.isNullOrBlank()) {
            etFeelingShort.setText(currentFeelingText)
        }

// clicking short field opens full-screen editor (FeelingsActivity)
        etFeelingShort.setOnClickListener {
            // pass current text to full editor
            val i = Intent(this, FeelingsActivity::class.java)
            i.putExtra("feeling_text", etFeelingShort.text.toString())
            feelingsLauncher.launch(i)
        }


        // Severity options
        val severityOptions = listOf("Mild", "Moderate", "Severe")
        spinnerSeverity.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, severityOptions)

        // Date pickers helper
        fun pickDate(editText: EditText) {
            val c = Calendar.getInstance()
            val dp = DatePickerDialog(this, { _, year, month, day ->
                val dateStr = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year)
                editText.setText(dateStr)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dp.show()
        }

        etDate.setOnClickListener { pickDate(etDate) }
        etStartDate.setOnClickListener { pickDate(etStartDate) }
        etEndDate.setOnClickListener { pickDate(etEndDate) }

        // Mic button: fill lastActivatedSymptomEditText and launch speech intent
        etSymptoms.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) lastActivatedSymptomEditText = etSymptoms
        }
        btnMic.setOnClickListener {
            lastActivatedSymptomEditText = etSymptoms
            // check permission at runtime
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO), 1500)
            } else {
                val intent = Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "Speak your symptoms")
                }
                try {
                    speechLauncher.launch(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Speech not supported on this device", Toast.LENGTH_SHORT).show()
                }
            }
        }
        etFeelingShort.setOnClickListener {
            val intent = Intent(this, FeelingsActivity::class.java)
            val prefill = etFeelingShort.text?.toString()
            if (!prefill.isNullOrEmpty()) intent.putExtra("prefill_feeling", prefill)
            feelingsLauncher.launch(intent)
        }


        // Build dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Log PMS Symptoms")
            .setView(dialogView)
            .setPositiveButton("Save", null) // set later to avoid automatic dismiss
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            if (currentFeelingText.isNotEmpty()) {
                etFeelingShort.setText(currentFeelingText)
            }
            val saveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            saveBtn.setOnClickListener {
                // Validation
                val pmsDateStr = etDate.text.toString().trim()
                val startDateStr = etStartDate.text.toString().trim()
                val endDateStr = etEndDate.text.toString().trim()
                val symptoms = etSymptoms.text.toString().trim()
                val severity = spinnerSeverity.selectedItem?.toString() ?: "Moderate"
                val other = etOtherInfo.text.toString().trim()
                val feeling = etFeelingShort.text.toString().ifEmpty { currentFeelingText }
                etFeelingShort.setText(feeling)
                currentFeelingText = feeling

                if (pmsDateStr.isEmpty() || startDateStr.isEmpty()) {
                    Toast.makeText(this, "Please enter PMS Date and Period Start Date.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!isValidDate(pmsDateStr) || !isValidDate(startDateStr) || (endDateStr.isNotEmpty() && !isValidDate(endDateStr))) {
                    Toast.makeText(this, "Please use date format DD/MM/YYYY.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Compute duration (days) safely
                val durationDays = safeCalculateDuration(pmsDateStr, startDateStr)

                val sdfDateTime = java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
                val currentDateTime = sdfDateTime.format(Date())

                val entryId = "PMSLog_${System.currentTimeMillis()}"
                val logText = """
                $currentDateTime
                PMS Date: $pmsDateStr
                Symptoms: $symptoms
                Severity: $severity
                Period Start: $startDateStr
                Period End: $endDateStr
                How I'm feeling: $feeling
                Other Info: $other
                Duration: $durationDays days
            """.trimIndent()

                // Save locally
                sharedPref.edit().putString(entryId, logText).apply()
                Toast.makeText(this, "PMS Symptoms saved!", Toast.LENGTH_SHORT).show()

                // Optionally save to Firestore
                if (SAVE_TO_FIRESTORE) {
                    try {
                        savePmsToFirestore(entryId, mapOf(
                            "timestamp" to com.google.firebase.Timestamp.now(),
                            "pmsDate" to pmsDateStr,
                            "symptoms" to symptoms,
                            "severity" to severity,
                            "periodStart" to startDateStr,
                            "periodEnd" to endDateStr,
                            "feeling" to feeling, // <-- added
                            "otherInfo" to other,
                            "durationDays" to durationDays
                        ))
                    } catch (e: Exception) {
                        // non-fatal — notify user or log
                        e.printStackTrace()
                    }
                }

                // contextual alerts
                when {
                    durationDays >= 7 -> {
                        AlertDialog.Builder(this)
                            .setTitle("Notice")
                            .setMessage("Your PMS symptoms lasted $durationDays days. You may want to investigate or consult a doctor.")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    severity.equals("Severe", ignoreCase = true) -> {
                        AlertDialog.Builder(this)
                            .setTitle("Severe symptoms")
                            .setMessage("You marked severe symptoms. If you feel unwell, consider seeking medical help.")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }

                dialog.dismiss()
            }
        }

        dialog.show()
    }


    // --- Replace any older calculateDuration with these helpers ---

    private fun isValidDate(dateStr: String): Boolean {
        return try {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(dateStr)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun safeCalculateDuration(pmsDate: String, periodStartDate: String): Int {
        return try {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val pms = sdf.parse(pmsDate)
            val periodStart = sdf.parse(periodStartDate)
            if (pms != null && periodStart != null) {
                val diff = periodStart.time - pms.time
                (diff / (1000 * 60 * 60 * 24)).toInt()
            } else 0
        } catch (e: Exception) {
            0
        }
    }

    // --- Firestore saving helper (optional) ---
    private fun savePmsToFirestore(docId: String, data: Map<String, Any>) {
        try {
            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            // save under collection "pms_logs" under user doc (UID or "anonymous")
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
            db.collection("users").document(uid)
                .collection("pms_logs")
                .document(docId)
                .set(data)
                .addOnSuccessListener { /* optional success handling */ }
                .addOnFailureListener { e -> e.printStackTrace() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun updateDailyTipBasedOnCycle(tvTips: TextView) {
        val tvTips = findViewById<TextView>(R.id.tvDailyTips)
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val lastDate = sharedPref.getString("lastPeriodDate", null)

        if (lastDate != null) {
            val parts = lastDate.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val year = parts[2].toInt()

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)

                val today = Calendar.getInstance()

                val diffDays = ((today.timeInMillis - calendar.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()

                val cycleDay = diffDays % 28  // Approximate 28-day cycle

                val tipText = StringBuilder()

                when {
                    cycleDay in 25..28 || cycleDay in 0..1 -> {
                        tipText.append("Your period may be approaching. Prepare with comfort measures and rest.\n")
                        tipText.append("🌿 Diet Tip: Include iron-rich foods like spinach, beetroot, jaggery.\n")
                        tipText.append("🧼 Hygiene Tip: Keep underwear clean and dry to avoid infections.\n")
                        tipText.append("💆‍♀️ Pain Relief Tip: Gentle stretching or a warm compress can ease discomfort.")
                    }
                    cycleDay in 2..6 -> {
                        tipText.append("During your period? Stay hydrated, eat iron-rich food, and rest well.\n")
                        tipText.append("🌿 Diet Tip: Drink warm liquids and eat light, nutritious meals.\n")
                        tipText.append("🧼 Hygiene Tip: Change sanitary products regularly.\n")
                        tipText.append("💆‍♀️ Pain Relief Tip: Apply heat packs and practice relaxation.")
                    }
                    cycleDay in 7..24 -> {
                        tipText.append("Post-period days are great for exercise and positive routines!\n")
                        tipText.append("🌿 Diet Tip: Balance your meals with fruits, veggies, and proteins.\n")
                        tipText.append("🧼 Hygiene Tip: Maintain regular intimate hygiene routines.\n")
                        tipText.append("💆‍♀️ Pain Relief Tip: No major relief needed, but light stretching keeps you active.")
                    }
                    else -> {
                        tipText.append("Take care of yourself today.\n")
                        tipText.append("🌿 Diet Tip: Eat a balanced, wholesome diet.\n")
                        tipText.append("🧼 Hygiene Tip: Stay clean and comfortable.\n")
                        tipText.append("💆‍♀️ Pain Relief Tip: Listen to your body.")
                    }
                }

                tvTips.text = tipText.toString()
                return
            }
        }

        // Fallback if no date found
        tvTips.text = "Track your period to receive personalized tips!"
    }
    private fun toggleSection(titleId: Int, layoutId: Int) {
        val title = findViewById<TextView>(titleId)
        val layout = findViewById<LinearLayout>(layoutId)
        title.setOnClickListener {
            layout.visibility = if (layout.visibility == LinearLayout.VISIBLE) LinearLayout.GONE else LinearLayout.VISIBLE
            title.text = if (layout.visibility == LinearLayout.VISIBLE)
                title.text.toString().replace("▼", "▲") else title.text.toString().replace("▲", "▼")
        }
    }

    private fun showLastPeriodToast() {
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val lastDate = sharedPref.getString("lastPeriodDate", "None")
        Toast.makeText(this, "Last Period Date: $lastDate", Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val selectedDate = "$day/${month + 1}/$year"
            Toast.makeText(this, "Period Date: $selectedDate", Toast.LENGTH_SHORT).show()

            val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
            sharedPref.edit().putString("lastPeriodDate", selectedDate).apply()

            schedulePeriodReminder()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun scheduleSelfCareReminder() {
        val calendar = Calendar.getInstance()

        val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            // If selected time is before current time, set for next day
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            val intent = Intent(this, ReminderReceiver::class.java)
            intent.putExtra("reminderType", "selfcare")
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            Toast.makeText(this, "Self-care reminder set for ${String.format("%02d:%02d", hourOfDay, minute)}", Toast.LENGTH_SHORT).show()

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

        timePicker.show()
    }


    private fun schedulePeriodReminder() {
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val lastDate = sharedPref.getString("lastPeriodDate", null)

        if (lastDate != null) {
            val parts = lastDate.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val year = parts[2].toInt()

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)

                // Calculate next period date (28 days cycle)
                calendar.add(Calendar.DAY_OF_MONTH, 28)

                // Set reminder 2 days before
                calendar.add(Calendar.DAY_OF_MONTH, -2)

                val intent = Intent(this, ReminderReceiver::class.java)
                intent.putExtra("reminderType", "period")  // Differentiate this reminder
                val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

                Toast.makeText(this, "Period reminder set!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please track your last period date first.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkPeriodReminder() {
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val lastDate = sharedPref.getString("lastPeriodDate", null)
        if (lastDate != null) {
            val parts = lastDate.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val year = parts[2].toInt()

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                calendar.add(Calendar.DAY_OF_MONTH, 28)
                calendar.add(Calendar.DAY_OF_MONTH, -2)

                if (System.currentTimeMillis() >= calendar.timeInMillis) {
                    sendPeriodNotification()
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1500) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // launch speech again if needed; user must tap mic again
                Toast.makeText(
                    this,
                    "Microphone permission granted. Tap mic to speak.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Microphone permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendPeriodNotification() {
        val builder = Notification.Builder(this, "reminderChannel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Period Reminder")
            .setContentText("Your period is expected soon. Stay prepared!")
            .setPriority(Notification.PRIORITY_HIGH)

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(1002, builder.build())
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url)))
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("reminderChannel", "Reminders", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun showBreathingExercise() {

        AlertDialog.Builder(this)
            .setTitle("Breathe In... Breathe Out")
            .setMessage("Inhale... Hold... Exhale... Repeat for a minute.")
            .setPositiveButton("Timer") { dialog, _ ->
                dialog.dismiss()
                showBreathingTimerDialog()
            }.show()


    }

    private fun showBreathingTimerDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_breathing, null)
        val messageText = dialogView.findViewById<TextView>(R.id.tvBreathingMessage)
        val timerView = dialogView.findViewById<TextView>(R.id.tvTimerView)
        val totalTimerView = dialogView.findViewById<TextView>(R.id.tvTotalTimer)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.show()
        startTotalTimer(totalTimerView, dialog)
        startBreathingCycle(messageText, timerView, 0, dialog)
    }

    private fun startTotalTimer(totalTimerView: TextView, dialog: AlertDialog) {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                totalTimerView.text = "Total Time Left: ${millisUntilFinished / 1000} sec"
            }

            override fun onFinish() {
                Toast.makeText(this@Swasthya, "Breathing Complete!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }.start()
    }

    private fun startBreathingCycle(messageText: TextView, timerView: TextView, cycle: Int, dialog: AlertDialog) {
        if (cycle >= 10) {
            Toast.makeText(this, "Great job!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            return
        }
        val handler = Handler(Looper.getMainLooper())
        var count = 3
        messageText.text = "Breathe In..."
        timerView.text = "$count"

        val runnable = object : Runnable {
            override fun run() {
                count--
                if (count > 0) {
                    timerView.text = "$count"
                    handler.postDelayed(this, 1000)
                } else {
                    startBreatheOut(messageText, timerView, cycle, dialog)
                }
            }
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun startBreatheOut(messageText: TextView, timerView: TextView, cycle: Int, dialog: AlertDialog) {
        val handler = Handler(Looper.getMainLooper())
        var count = 3
        messageText.text = "Breathe Out..."
        timerView.text = "$count"

        val runnable = object : Runnable {
            override fun run() {
                count--
                if (count > 0) {
                    timerView.text = "$count"
                    handler.postDelayed(this, 1000)
                } else {
                    startBreathingCycle(messageText, timerView, cycle + 1, dialog)
                }
            }
        }
        handler.postDelayed(runnable, 1000)
    }

}
