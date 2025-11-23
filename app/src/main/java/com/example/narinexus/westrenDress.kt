package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class westrenDress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_westren_dress)

        val westernDressProduct1Button: ImageButton = findViewById(R.id.westernDressProduct1Button)
        westernDressProduct1Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=bodycon+dresses&sid=clo%2Codx%2Cmaj%2Cjhy&as=on&as-show=on&otracker=AS_QueryStore_OrganicAutoSuggest_1_8_na_na_na&otracker1=AS_QueryStore_OrganicAutoSuggest_1_8_na_na_na&as-pos=1&as-type=RECENT&suggestionId=bodycon+dresses%7CWomen%27s+Dresses&requestId=d64a5beb-ef32-4a8c-b538-01b62ba06fe6&as-searchtext=bodycon%20dresses"))
            startActivity(intent)
        }
        val westernDressProduct2Button: ImageButton = findViewById(R.id.westernDressProduct2Button)
        westernDressProduct2Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=a-line%20dress&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"))
            startActivity(intent)
        }
        val westernDressProduct3Button: ImageButton = findViewById(R.id.westernDressProduct3Button)
        westernDressProduct3Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=skater+dress+for+women&sid=clo%2Codx%2Cmaj%2Cjhy&as=on&as-show=on&otracker=AS_QueryStore_OrganicAutoSuggest_1_7_na_na_ps&otracker1=AS_QueryStore_OrganicAutoSuggest_1_7_na_na_ps&as-pos=1&as-type=RECENT&suggestionId=skater+dress+for+women%7CWomen%27s+Dresses&requestId=e29715ab-949c-4679-9802-f4941aa56f6c&as-searchtext=skater%20"))
            startActivity(intent)
        }
        val westernDressProduct4Button: ImageButton = findViewById(R.id.westernDressProduct4Button)
        westernDressProduct4Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com/search?q=maxi+dress+for+women&sid=clo%2Codx%2Cmaj%2Cjhy&as=on&as-show=on&otracker=AS_QueryStore_OrganicAutoSuggest_2_14_na_na_na&otracker1=AS_QueryStore_OrganicAutoSuggest_2_14_na_na_na&as-pos=2&as-type=RECENT&suggestionId=maxi+dress+for+women%7CWomen%27s+Dresses&requestId=f1dcde6f-015e-4be6-b508-7501e0110956&as-searchtext=maxi%20%20for%20women"))
            startActivity(intent)
        }
    }
}
