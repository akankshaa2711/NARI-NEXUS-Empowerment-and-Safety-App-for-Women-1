package com.example.narinexus

data class FoodItem(
    val id: String,
    val title: String,
    val shortDesc: String,
    val imageRes: Int,
    val youtubeUrl: String = ""
)
