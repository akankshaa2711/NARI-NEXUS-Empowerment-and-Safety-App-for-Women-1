package com.example.narinexus

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodsAdapter(
    private val context: Context,
    private val items: List<FoodItem>
) : RecyclerView.Adapter<FoodsAdapter.FoodVH>() {

    class FoodVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgFood)
        val title: TextView = itemView.findViewById(R.id.tvFoodTitle)
        val desc: TextView = itemView.findViewById(R.id.tvFoodDesc)
        val btnWatch: Button = itemView.findViewById(R.id.btnAvailableOn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodVH(v)
    }

    override fun onBindViewHolder(holder: FoodVH, position: Int) {
        val f = items[position]
        holder.img.setImageResource(f.imageRes)
        holder.title.text = f.title
        holder.desc.text = f.shortDesc
        holder.btnWatch.text = "Watch Recipe"

        holder.btnWatch.setOnClickListener {
            if (f.youtubeUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(f.youtubeUrl))
                context.startActivity(intent)
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No link")
                    .setMessage("Recipe link not available for this item.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
