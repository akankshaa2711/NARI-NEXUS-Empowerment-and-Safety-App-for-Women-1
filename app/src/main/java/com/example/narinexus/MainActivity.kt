package com.example.narinexus

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import android.view.Gravity
import android.view.View


class MainActivity : AppCompatActivity() {

    private lateinit var sliderContainer: LinearLayout
    private lateinit var scrollView: HorizontalScrollView
    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0
    private lateinit var imageList: List<Int>

    private val allImages = listOf(
        R.drawable.motivational, // fixed first image
        R.drawable.motivation1,
        R.drawable.motivational3,
        R.drawable.motivational4,
        R.drawable.motivational5
    )

    private val CALL_REQUEST_CODE = 100
    private val phoneNumber = "1234567890"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

// --- Drawer wiring (paste this right after setSupportActionBar(toolbar)) ---
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.navigation_view)

// Setup ActionBarDrawerToggle (hamburger)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

// add these imports at top of MainActivity.kt (if not already present)
// import android.widget.AutoCompleteTextView
// import android.view.inputmethod.InputMethodManager
// import android.widget.ArrayAdapter

// --- Header: profile + search wiring ---
        val header = if (navView.headerCount > 0) navView.getHeaderView(0) else null
        header?.let { h ->
            // profile picture click (you already had this)
            val profilePic = h.findViewById<ImageView?>(R.id.drawer_profile_pic)
            profilePic?.setOnClickListener {
                // optional: open profile activity
                // startActivity(Intent(this, ProfileActivity::class.java))
                drawerLayout.closeDrawer(Gravity.START)
            }

            // --- SEARCH BOX in header (AutoCompleteTextView) ---
            val searchAuto = h.findViewById<AutoCompleteTextView>(R.id.nav_search)

            // Make sure nav_search exists in your header layout (see XML snippet further below)
            if (searchAuto != null) {
                // labels shown in suggestions (keep these synced with your menu labels)
                val drawerLabels = listOf(
                    "Body Positivity",
                    "HerVerse",
                    "Mitra Mandal Chatroom",
                    "Mood vs Cycle",
                    "Nari Picks",
                    "NutriNest",
                    "PMS Diary",
                    "Swasthya Health Guide",
                    "About",
                    "Contact Us",
                    "Feedback",
                    "Helpline Numbers",
                    "Discover More"
                )

                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, drawerLabels)
                searchAuto.setAdapter(adapter)
                searchAuto.threshold = 1

                // when user taps a suggested item
                searchAuto.setOnItemClickListener { parent, _, position, _ ->
                    val selected = parent.getItemAtPosition(position) as String
                    // perform navigation using the helper you already added
                    performDrawerNavigation(selected, drawerLayout)
                    // clear text and hide keyboard
                    searchAuto.setText("")
                    hideKeyboard()
                }

                // when user presses "search" on keyboard, try to match / navigate
                searchAuto.setOnEditorActionListener { v, _, _ ->
                    val text = v.text.toString().trim()
                    if (text.isNotEmpty()) {
                        // case-insensitive exact or prefix match
                        val match = drawerLabels.firstOrNull {
                            it.equals(text, ignoreCase = true) || it.startsWith(text, ignoreCase = true)
                        }
                        if (match != null) {
                            performDrawerNavigation(match, drawerLayout)
                            searchAuto.setText("")
                            hideKeyboard()
                        }
                    }
                    false
                }
            }
        }



// handle navigation item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_bodypositivity -> startActivity(Intent(this, bodypositivity::class.java))
                R.id.nav_books -> startActivity(Intent(this, BooksActivity::class.java))
                R.id.nav_foods -> startActivity(Intent(this, FoodsActivity::class.java))

                R.id.nav_mitra -> startActivity(Intent(this, chatroom::class.java))
                R.id.nav_mood_graph -> startActivity(Intent(this, MoodGraphActivity::class.java))
                R.id.nav_naripicks -> startActivity(Intent(this, products::class.java))
                R.id.nav_pms_diary -> savedPMSlog()
                R.id.nav_swasthya -> startActivity(Intent(this, Swasthya::class.java))
                R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))

                R.id.nav_contactus -> startActivity(Intent(this, contactus::class.java))
                R.id.nav_feedback -> startActivity(Intent(this, FeedbackFormActivity::class.java))
                R.id.nav_helpline -> startActivity(Intent(this, helplineNumbers::class.java))
                R.id.nav_discover -> startActivity(Intent(this,discover_more::class.java))

                else -> { /* no-op */ }
            }
            drawerLayout.closeDrawer(Gravity.START)
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Feature Buttons
        val swastha = findViewById<Button>(R.id.btnSwasthya)
        swastha.setOnClickListener {
            val i = Intent(this, Swasthya::class.java)
            startActivity(i)
        }

        val bodybtn = findViewById<Button>(R.id.btnNewFeature)
        bodybtn.setOnClickListener {
            val k = Intent(this, bodypositivity::class.java)
            startActivity(k)
        }

        val helpbtn = findViewById<Button>(R.id.helplinebtn)
        helpbtn.setOnClickListener {
            val j = Intent(this, helplineNumbers::class.java)
            startActivity(j)
        }
        val nariPick = findViewById<Button>(R.id.btnNariPicks)
        nariPick.setOnClickListener {
            val m = Intent(this, products::class.java)
            startActivity(m)
        }
        // Mitra Mandal Chatroom button
        val mitraBtn = findViewById<Button>(R.id.btnMitraMandal)
        mitraBtn.setOnClickListener {
            val intent = Intent(this, chatroom::class.java)
            startActivity(intent)
        }
        val discoverBtn=findViewById<Button>(R.id.btndiscover)
        discoverBtn.setOnClickListener {
            val intent = Intent(this,discover_more::class.java)
            startActivity(intent)
        }



        // 🖼️ Image Slider Setup
        sliderContainer = findViewById(R.id.sliderContainer)
        scrollView = findViewById(R.id.imageScrollView)

        val shuffledImages = allImages.drop(1).shuffled(Random(System.currentTimeMillis()))
        imageList = listOf(allImages[0]) + shuffledImages

        for (imageRes in imageList) {
            val imageView = ImageView(this).apply {
                setImageResource(imageRes)
                layoutParams = LinearLayout.LayoutParams(800, 600).apply {
                    setMargins(24, 0, 24, 0)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            sliderContainer.addView(imageView)
        }

        startAutoScroll()

        // 📞 Call Permission and Button
        val per = findViewById<Button>(R.id.perone)
        per.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                makeCall()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_REQUEST_CODE
                )
            }
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex < sliderContainer.childCount - 1) {
                    currentIndex++
                } else {
                    currentIndex = 0
                }
                val scrollX = sliderContainer.getChildAt(currentIndex).left
                scrollView.smoothScrollTo(scrollX, 0)
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun makeCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                val shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CALL_PHONE
                )

                if (shouldShow) {
                    AlertDialog.Builder(this)
                        .setTitle("Permission Required")
                        .setMessage("This app needs call permission")
                        .setPositiveButton("Grant") { _, _ ->
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                CALL_REQUEST_CODE
                            )
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Permission Permanently Denied")
                        .setMessage("Please go to settings and grant permission manually")
                        .setPositiveButton("Open Settings") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_contact -> {
                val intent = Intent(this, contactus::class.java)
                startActivity(intent)
                Toast.makeText(this, "Contact Us selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_pms_diary -> {
                savedPMSlog()
                true
            }
            R.id.menu_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savedPMSlog() {
        val sharedPref = getSharedPreferences("SwasthyaPrefs", MODE_PRIVATE)
        val allEntries = sharedPref.all

        val pmsLogs = allEntries.filterKeys { it.startsWith("PMSLog_") }

        if (pmsLogs.isEmpty()) {
            Toast.makeText(this, "No PMS logs found!", Toast.LENGTH_SHORT).show()
            return
        }

        val logKeys = pmsLogs.keys.toList()
        val logValues = pmsLogs.values.map { it.toString() }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("PMS Symptom History")
            .setItems(logValues) { _, which ->
                AlertDialog.Builder(this)
                    .setTitle("Delete Entry")
                    .setMessage("Do you want to delete this log?\n\n${logValues[which]}")
                    .setPositiveButton("Delete") { _, _ ->
                        sharedPref.edit().remove(logKeys[which]).apply()
                        Toast.makeText(this, "Log deleted!", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            .setPositiveButton("Close", null)
            .show()
    }
    override fun onBackPressed() {
        if (shouldAskForFeedback()) {
            showRatingDialog() // or showFeedbackDialog()
            updateFeedbackTimestamp()
        } else {
            super.onBackPressed() // call parent only when not showing feedback
        }
    }


    // ⭐ Show rating stars
    private fun showRatingDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_feedback, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        AlertDialog.Builder(this)
            .setTitle("How would you rate our app?")
            .setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                val rating = ratingBar.rating
                Toast.makeText(this, "You rated: $rating stars", Toast.LENGTH_SHORT).show()
                if (rating >= 4) {
                    Toast.makeText(this, "Thanks for the love! ❤️", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "We’d love to improve for you!", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()
                showFeedbackForm()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // 📝 Feedback Form
    private fun showFeedbackForm() {
        val feedbackView = layoutInflater.inflate(R.layout.feedback_form, null)

        AlertDialog.Builder(this)
            .setTitle("We value your feedback")
            .setView(feedbackView)
            .setPositiveButton("Submit") { dialog, _ ->
                // TODO: collect EditText & RatingBar inputs
                Toast.makeText(this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun shouldAskForFeedback(): Boolean {
        val prefs = getSharedPreferences("FeedbackPrefs", MODE_PRIVATE)
        val lastTime = prefs.getLong("last_feedback", 0L)
        val sixMonthsMillis = 6L * 30 * 24 * 60 * 60 * 1000 // ~6 months
        return System.currentTimeMillis() - lastTime > sixMonthsMillis
    }

    private fun updateFeedbackTimestamp() {
        val prefs = getSharedPreferences("FeedbackPrefs", MODE_PRIVATE)
        prefs.edit().putLong("last_feedback", System.currentTimeMillis()).apply()
    }
    private fun performDrawerNavigation(label: String, drawerLayout: DrawerLayout) {
        when (label) {
            "Body Positivity" -> startActivity(Intent(this, bodypositivity::class.java))
            "HerVerse" -> startActivity(Intent(this, BooksActivity::class.java)) // your books screen
            "Mitra Mandal Chatroom" -> startActivity(Intent(this, chatroom::class.java))
            "Mood vs Cycle" -> startActivity(Intent(this, MoodGraphActivity::class.java))
            "Nari Picks" -> startActivity(Intent(this, products::class.java))
            "NutriNest" -> startActivity(Intent(this, FoodsActivity::class.java))
            "PMS Diary" -> savedPMSlog()
            "Swasthya Health Guide" -> startActivity(Intent(this, Swasthya::class.java))
            "About" -> startActivity(Intent(this, AboutActivity::class.java))
            "Contact Us" -> startActivity(Intent(this, contactus::class.java))
            "Discover More" -> startActivity(Intent(this, discover_more::class.java))
            "Feedback" -> startActivity(Intent(this, FeedbackFormActivity::class.java))
            "Helpline Numbers" -> startActivity(Intent(this, helplineNumbers::class.java))
            else -> {
                // fallback: if you also want to match nav menu item IDs, you can call navView.setCheckedItem...
            }
        }
        drawerLayout.closeDrawer(Gravity.START)
    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        currentFocus?.let { imm?.hideSoftInputFromWindow(it.windowToken, 0) }
    }

}
