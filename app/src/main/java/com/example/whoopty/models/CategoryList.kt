package com.example.whoopty.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(val categories: Array<Category>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryList

        if (!categories.contentEquals(other.categories)) return false

        return true
    }

    override fun hashCode(): Int {
        return categories.contentHashCode()
    }
}