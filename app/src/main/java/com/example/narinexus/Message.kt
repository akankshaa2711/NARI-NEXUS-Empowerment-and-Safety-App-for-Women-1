package com.example.narinexus


data class Message(
    val senderId: String = "",
    val message: String? = null,
    val imageBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)