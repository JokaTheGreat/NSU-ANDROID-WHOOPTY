package com.example.whoopty.mealcard

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whoopty.models.Meal
import com.example.whoopty.utils.StringFormatter

class MealCardAdapter(
    private val meal: List<Meal>,
    private val ingredientList: List<String>,
    private val measureList: List<String>,
    private val activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = when (meal.size) {
        1 -> when (meal[0].strYoutube) {
            null -> 3
            "" -> 3
            else -> 4
        }
        else -> 0
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MealCardTitleFragment(meal[0].strMeal, meal[0].strMealThumb)
        1 -> MealCardIngredientsFragment(ingredientList, measureList, activity)
        2 -> MealCardRecipeFragment(
            StringFormatter.splitByNewStringSymbol(meal[0].strInstructions!!),
            activity
        )
        else -> MealCardLinksFragment(meal[0].strYoutube!!, activity)
    }
}