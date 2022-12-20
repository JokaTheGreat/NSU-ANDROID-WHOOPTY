package com.example.whoopty.models

import kotlinx.serialization.Serializable

@Serializable
data class Meal(val strMeal: String, val strMealThumb: String, val idMeal: Int)
