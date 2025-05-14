package com.myrealtrip.openai.dto

data class TravelRecommendation(
    val destination: String,
    val bestTimeToVisit: String,
    val attractions: List<String>,
    val localCuisine: List<String>,
    val estimatedBudget: Int
) 