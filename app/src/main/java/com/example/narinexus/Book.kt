package com.example.narinexus

data class Book(
    val title: String,
    val author: String,
    val rating: Float,
    val coverRes: Int,   // drawable resource id
    val type: String,    // "pdf" or "link"
    val url: String      // pdf path or external link
)
