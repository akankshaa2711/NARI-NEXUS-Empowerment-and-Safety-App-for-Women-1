package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val recycler = findViewById<RecyclerView>(R.id.recyclerBooks)
        recycler.layoutManager = LinearLayoutManager(this)

        val books = listOf(
            Book("A Room of One’s Own", "Virginia Woolf", 4.5f, R.drawable.book1, "link", "https://maulanaazadcollegekolkata.ac.in/pdf/open-resources/A-Room-of-Ones-Own-Virginia-Woolf-PDF.pdf"),
            Book("The Subjection of Women", "John Stuart Mill", 4.2f, R.drawable.book2, "link", "https://www.earlymoderntexts.com/assets/pdfs/mill1869.pdf"),
            Book("International-Book-Women-Empowerment-Yesterday-Today-and-Tomorrow", "Dr. Dilip Barsagade", 4.0f, R.drawable.book3, "link", "https://gurukuljournal.com/wp-content/uploads/2017/08/International-Book-Women-Empowerment-Yesterday-Today-and-Tomorrow.pdf"),
            Book("Our Bodies, Ourselves", "Boston Women’s Health Collective", 4.7f, R.drawable.book4, "link", "https://cdn.bookey.app/files/pdf/book/en/our-bodies-ourselves.pdf"),
            Book("Empowering Women at Work-2020 Report", "UN Women", 4.3f, R.drawable.book5, "link", "https://www.ilo.org/sites/default/files/wcmsp5/groups/public/@ed_emp/@emp_ent/@multi/documents/publication/wcms_756721.pdf"),

            Book("Becoming", "Michelle Obama", 4.8f, R.drawable.book6, "link", "https://cdn.bookey.app/files/pdf/book/en/becoming-by-michelle-obama.pdf"),
            Book("Women, Ageing and Health: A Framework for Action", "WHO", 4.6f, R.drawable.book7, "link", "https://iris.who.int/bitstream/handle/10665/43810/9789241563529_eng.pdf"),
            Book("Lean In", "Sheryl Sandberg", 4.1f, R.drawable.book8, "link", "https://cdn.bookey.app/files/pdf/book/en/lean-in.pdf"),
            Book("The Moment of Lift", "Melinda Gates", 4.4f, R.drawable.book9, "link", "https://cdn.bookey.app/files/pdf/book/en/the-moment-of-lift.pdf"),
            Book("Invisible Women", "Caroline Criado Perez", 4.5f, R.drawable.book10, "link", "https://c02.purpledshub.com/uploads/sites/41/2020/03/InvisibleWomen-intro-chapter-1-and-2-5521d0f.pdf")
        )

        recycler.adapter = BooksAdapter(this, books)

        // Read more button
        findViewById<Button>(R.id.btnMoreBooks).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?tbm=bks&q=women+empowerment+health+life"))
            startActivity(intent)
        }
    }
}
