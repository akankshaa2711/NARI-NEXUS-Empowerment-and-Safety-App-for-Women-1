package com.example.narinexus

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(private val context: Context, private val books: List<Book>) :
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: ImageView = itemView.findViewById(R.id.imgCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.imgCover.setImageResource(book.coverRes)
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = "By ${book.author}"

        // If you want half-stars visible, set step size to 0.5 in XML and assign actual float rating
        holder.ratingBar.rating = book.rating

        holder.itemView.setOnClickListener {
            try {
                // Always open the book.url in browser (works for "link" type)
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(book.url))
                context.startActivity(i)
            } catch (e: Exception) {
                e.printStackTrace()
                // Don't crash; optionally show a toast (commented out)
                // Toast.makeText(context, "Cannot open link", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = books.size
}
