package com.example.narinexus

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.firebase.firestore.Query
import android.util.Base64
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.InputStream

class chatroom : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonSend: ImageButton
    private lateinit var buttonImage: ImageButton
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    private val db = FirebaseFirestore.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: "user1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        recyclerView = findViewById(R.id.recyclerView)
        editText = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        buttonImage = findViewById(R.id.buttonImage)

        adapter = MessageAdapter(messages, currentUserId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonSend.setOnClickListener { sendTextMessage() }
        buttonImage.setOnClickListener { pickImage() }

        listenForMessages()
    }

    private fun sendTextMessage() {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            val msg = Message(senderId = currentUserId, message = text)
            db.collection("messages").add(msg)
            editText.text.clear()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data ?: return
            val base64 = uriToBase64(uri)
            val msg = Message(senderId = currentUserId, imageBase64 = base64)
            db.collection("messages").add(msg)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uriToBase64(uri: Uri): String {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return ""
        inputStream.close()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun listenForMessages() {
        db.collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                messages.clear()
                snapshot?.toObjects(Message::class.java)?.let { messages.addAll(it) }
                adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messages.size - 1)
            }
    }
}