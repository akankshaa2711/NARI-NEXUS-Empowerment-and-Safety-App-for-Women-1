package com.example.narinexus

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val etFirstName = findViewById<EditText>(R.id.etFirst)
        val etLastName = findViewById<EditText>(R.id.etLast)
        val etDob = findViewById<EditText>(R.id.edob)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etUsername = findViewById<EditText>(R.id.etUser)
        val etOtp = findViewById<EditText>(R.id.etOtp)
        val spinner = findViewById<Spinner>(R.id.spinnerCC)

        val rbEmail = findViewById<RadioButton>(R.id.rbEmail)
        val rbPhone = findViewById<RadioButton>(R.id.rbPhone)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Date Picker
        etDob.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                etDob.setText(String.format("%02d/%02d/%d", d, m + 1, y))
            }, year, month, day).show()
        }

        // Radio toggle
        rbEmail.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etEmail.visibility = View.VISIBLE
                etPassword.visibility = View.VISIBLE
                etOtp.visibility = View.GONE
            }
        }

        rbPhone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etEmail.visibility = View.GONE
                etPassword.visibility = View.GONE
                etOtp.visibility = View.VISIBLE
            }
        }

        btnRegister.setOnClickListener {
            val phone = spinner.selectedItem.toString() + etPhone.text.toString()
            val otp = etOtp.text.toString()

            if (rbEmail.isChecked) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserToFirestore()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

            } else if (rbPhone.isChecked) {
                if (storedVerificationId.isEmpty()) {
                    sendOtp(phone)
                } else {
                    verifyOtp(otp)
                }
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(applicationContext, "OTP Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    storedVerificationId = verificationId
                    resendToken = token
                    Toast.makeText(applicationContext, "OTP Sent ✅", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOtp(code: String) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserToFirestore()
                } else {
                    Toast.makeText(this, "OTP Invalid ❌", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore() {
        val etFirstName = findViewById<EditText>(R.id.etFirst)
        val etLastName = findViewById<EditText>(R.id.etLast)
        val etDob = findViewById<EditText>(R.id.edob)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etUsername = findViewById<EditText>(R.id.etUser)
        val spinner = findViewById<Spinner>(R.id.spinnerCC)

        val uid = auth.currentUser?.uid ?: return

        val user = hashMapOf(
            "firstName" to etFirstName.text.toString(),
            "lastName" to etLastName.text.toString(),
            "dob" to etDob.text.toString(),
            "email" to etEmail.text.toString(),
            "phone" to spinner.selectedItem.toString() + etPhone.text.toString(),
            "username" to etUsername.text.toString()
        )

        FirebaseFirestore.getInstance().collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User data saved to Firestore ✅", Toast.LENGTH_SHORT).show()

                // 🔁 Redirect to LoginActivity after saving user data
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save user data ❌", Toast.LENGTH_SHORT).show()
            }
    }
}
