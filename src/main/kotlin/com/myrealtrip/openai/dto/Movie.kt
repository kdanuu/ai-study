package com.myrealtrip.openai.dto

data class Movie(
    val title: String,
    val director: String,
    val year: Int,
    val genre: String,
    val rating: Double
) 