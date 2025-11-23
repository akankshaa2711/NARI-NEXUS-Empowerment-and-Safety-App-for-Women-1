package com.example.narinexus

import android.os.Bundle
import android.content.Intent
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class bodypositivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodypositivity)

        val quotes = listOf(
            "💬 Pooja, 32: I was called fat in school, now I run a fitness club.",
            "💬 Anjali, 28: I used to hide my scars. Now I wear them like armor.",
            "💬 Ritu, 25: They said I wasn’t pretty enough. Now I model for campaigns.",
            "💬 Sanya, 40: After my surgery, I hated my reflection. Now I celebrate it.",
            "💬 Neha, 31: I stopped apologizing for my body. It’s not a mistake.",
            "💬 Tanya, 27: I gained weight, not shame. I'm still glowing.",
            "💬 Divya, 22: My belly isn’t flat, but my smile is real.",
            "💬 Priya, 34: Cellulite and self-worth aren’t connected. I love both.",)
        val quoteview = findViewById<TextView>(R.id.storyText)
        quoteview.text = quotes.random()

        val quotes2 = listOf(
            "💬 Isha, 19: I have stretch marks, acne, and confidence — all natural.",
            "💬 Meena, 35: I stopped chasing 'perfect'. Now I chase peace.",
            "💬 Kavya, 30: People laughed at my weight. I laughed louder with self-love.",
            "💬 Zoya, 29: My body tells my story. Every curve, every line is mine.",
            "💬 Aisha, 29: I wear what I love — not what hides me.",
            "💬 Nidhi, 26: No filter. No shame. Just me.",
            "💬 Sneha, 30: I’m more than my mirror. I’m my mind and heart.",
            "💬 Bhavna, 36: My body changed after motherhood, but so did my strength.",)
        val quoteview2 = findViewById<TextView>(R.id.storyText2)
        quoteview2.text = quotes2.random()

        // buttons
        val buttonLaxmi = findViewById<ImageButton>(R.id.buttonLaxmi)
        val buttonHarnaam = findViewById<ImageButton>(R.id.buttonHarnaam)
        val buttonUsha = findViewById<ImageButton>(R.id.buttonUsha)
        val buttonReshma = findViewById<ImageButton>(R.id.buttonReshma)
        val buttonMeghna = findViewById<ImageButton>(R.id.buttonMeghna)
        val buttonAnjali = findViewById<ImageButton>(R.id.buttonAnjali)
        val buttonWinnie = findViewById<ImageButton>(R.id.buttonWinnie)

        val responseInput = findViewById<EditText>(R.id.responseInput)
        val submitB = findViewById<Button>(R.id.submitBtn)

        //hiding button
        submitB.visibility = View.GONE

        responseInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val l = if (s != null) s.length else 0
                submitB.visibility = if (l > 0) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        //button functioning
        val editText = findViewById<EditText>(R.id.responseInput)
        val submitButton = findViewById<Button>(R.id.submitBtn)
        val m = findViewById<TextView>(R.id.selfLoveMessage)
        submitButton.setOnClickListener {
            val usertext = editText.text.toString().trim()
            val b = AlertDialog.Builder(this)
            b.setTitle("Self-Love Entry")
            b.setMessage("Do you want to add more about what you love about yourself?")
            b.setPositiveButton("No") { dialog, _ ->
                editText.text.clear()
                Toast.makeText(this, "Thank you for sharing 💜", Toast.LENGTH_SHORT).show()
            }
            b.setNegativeButton("Yes") { dialog, _ ->
                dialog.dismiss()
            }
            b.setNeutralButton("Save Draft") { dialog, _ ->
                Toast.makeText(this, "Saved as draft for later.", Toast.LENGTH_SHORT).show()
            }
            b.create().show()
            m.text = "It’s beautiful that you’re loving yourself today."
            m.visibility = TextView.VISIBLE
        }

        //onClickListeners for all Horizontal Images
        buttonLaxmi.setOnClickListener {
            val intent = Intent(this, LaxmiActivity::class.java)
            startActivity(intent)
        }

        buttonHarnaam.setOnClickListener {
            val intent = Intent(this, HarnaamActivity::class.java)
            startActivity(intent)
        }

        buttonUsha.setOnClickListener {
            val intent = Intent(this, UshaActivity::class.java)
            startActivity(intent)
        }

        buttonReshma.setOnClickListener {
            val intent = Intent(this, ReshmaActivity::class.java)
            startActivity(intent)
        }

        buttonMeghna.setOnClickListener {
            val intent = Intent(this, MeghnaActivity::class.java)
            startActivity(intent)
        }

        buttonAnjali.setOnClickListener {
            val intent = Intent(this, AnjaliActivity::class.java)
            startActivity(intent)
        }

        buttonWinnie.setOnClickListener {
            val intent = Intent(this, WinnieActivity::class.java)
            startActivity(intent)
        }

    }
}