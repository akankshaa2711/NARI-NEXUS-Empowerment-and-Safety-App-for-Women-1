package com.example.narinexus

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val data: List<Message>, private val uid: String)
    : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textMessage)
        val imageView: ImageView = itemView.findViewById(R.id.imageMessage)
        val container: LinearLayout = itemView.findViewById(R.id.messageContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = data[position]

        if (msg.senderId == uid) {
            holder.container.setBackgroundColor(0xFFA5D6A7.toInt()) // Green for sender
        } else {
            holder.container.setBackgroundColor(0xFFD3D3D3.toInt()) // Light gray for receiver
        }

        if (!msg.imageBase64.isNullOrEmpty()) {
            holder.textView.visibility = View.GONE
            holder.imageView.visibility = View.VISIBLE
            val bytes = Base64.decode(msg.imageBase64, Base64.DEFAULT)
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.imageView.setImageBitmap(bmp)
        } else {
            holder.imageView.visibility = View.GONE
            holder.textView.visibility = View.VISIBLE
            holder.textView.text = msg.message
        }
    }

    override fun getItemCount(): Int = data.size
}