package com.example.narinexus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class contactus : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var submitButton: Button

    // tappable rows and links
    private lateinit var phoneRow1: LinearLayout
    private lateinit var phoneRow2: LinearLayout
    private lateinit var phoneRow3: LinearLayout
    private lateinit var phoneRow4: LinearLayout

    private lateinit var emailRow: LinearLayout
    private lateinit var instagramRow: LinearLayout
    private lateinit var facebookRow: LinearLayout

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactus)

        // form fields
        nameEditText = findViewById(R.id.editTextName)
        emailEditText = findViewById(R.id.editTextEmail)
        messageEditText = findViewById(R.id.editTextMessage)
        submitButton = findViewById(R.id.buttonSubmit)

        // rows
        phoneRow1 = findViewById(R.id.phoneRow1)
        phoneRow2 = findViewById(R.id.phoneRow2)
        phoneRow3 = findViewById(R.id.phoneRow3)
        phoneRow4 = findViewById(R.id.phoneRow4)

        emailRow = findViewById(R.id.emailRow)
        instagramRow = findViewById(R.id.instagramRow)
        facebookRow = findViewById(R.id.facebookRow)

        // phone numbers (these are visible inside TextViews in XML but list them again here)
        val phone1 = findViewById<TextView>(R.id.phone1).text.toString().trim()
        val phone2 = findViewById<TextView>(R.id.phone2).text.toString().trim()
        val phone3 = findViewById<TextView>(R.id.phone3).text.toString().trim()
        val phone4 = findViewById<TextView>(R.id.phone4).text.toString().trim()

        // Set click listeners for phone rows (open dialer)
        phoneRow1.setOnClickListener { openDialer(phone1) }
        phoneRow2.setOnClickListener { openDialer(phone2) }
        phoneRow3.setOnClickListener { openDialer(phone3) }
        phoneRow4.setOnClickListener { openDialer(phone4) }

        // Email row opens email composer
        val emailAddress = findViewById<TextView>(R.id.emailLink).text.toString().trim()
        emailRow.setOnClickListener { openEmail(emailAddress) }

        // Instagram row: try open app, fallback to browser
        val instagramUrl = "https://instagram.com/narinexus25"
        instagramRow.setOnClickListener { openSocial("com.instagram.android", instagramUrl) }

        // Facebook row
        val facebookUrl = "https://facebook.com/narinexus25"
        facebookRow.setOnClickListener { openSocial("com.facebook.katana", facebookUrl) }

        // Submit button (save to Firestore)
        submitButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val message = messageEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                // Show success toast (later you can link it to Firebase/Email API)
                Toast.makeText(
                    this,
                    "Message sent successfully to Nari Nexus Team!",
                    Toast.LENGTH_LONG
                ).show()
            }
            val contactData = hashMapOf(
                "name" to name,
                "email" to email,
                "message" to message,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("contact_messages")
                .add(contactData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show()
                    nameEditText.text.clear()
                    emailEditText.text.clear()
                    messageEditText.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error sending message", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun openDialer(number: String) {
        val uri = Uri.parse("tel:$number")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        try {
            startActivity(intent)
        } catch (ex: Exception) {
            Toast.makeText(this, "Cannot open dialer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openEmail(address: String) {
        // ACTION_SENDTO with mailto: keeps it simple and opens default email client
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$address")
            putExtra(Intent.EXTRA_SUBJECT, "Nari Nexus - User Message")
        }
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSocial(packageName: String, webUrl: String) {
        // Try to open native app first
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            // open the app to the profile via ACTION_VIEW + url as a safety fallback
            try {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                i.setPackage(packageName)
                startActivity(i)
                return
            } catch (e: Exception) {
                // fallback to browser below
            }
        }
        // fallback to browser
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(browserIntent)
        } catch (ex: Exception) {
            Toast.makeText(this, "Cannot open link", Toast.LENGTH_SHORT).show()
        }
    }
}
