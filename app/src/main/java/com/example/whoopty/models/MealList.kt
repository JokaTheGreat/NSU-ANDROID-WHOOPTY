package com.example.whoopty.models

import kotlinx.serialization.Serializable

@Serializable
data class MealList(val meals: Array<Meal>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealList

        if (!meals.contentEquals(other.meals)) return false

        return true
    }

    override fun hashCode(): Int {
        return meals.contentHashCode()
    }

}
