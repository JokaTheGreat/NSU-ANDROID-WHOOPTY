package com.example.whoopty.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val idCategory: Int,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)