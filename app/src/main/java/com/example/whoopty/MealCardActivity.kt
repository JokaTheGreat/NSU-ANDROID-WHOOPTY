package com.example.whoopty

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.whoopty.models.Meal
import com.example.whoopty.models.MealList
import com.example.whoopty.utils.StringFormater
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.relex.circleindicator.CircleIndicator3
import java.net.HttpURLConnection
import java.net.URL

class MealCardActivity : AppCompatActivity() {
    private var mealId: Int? = null
    private var fragmentsList = mutableListOf<Fragment>()
    private var ingredientList = mutableListOf<String>()
    private var measureList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_card)

        mealId = intent.getIntExtra("mealId", -1)

        Thread {
            val meal = getMealDetails()
            postToList(meal)
            setFragmentsToList(meal)
            updateUI()
        }.start()

        val backToListView = findViewById<ImageView>(R.id.meal_card_back_to_meals)
        backToListView.setOnClickListener { onBackPressed() }

        val viewPager = findViewById<ViewPager2>(R.id.meal_card_view_pager)
        viewPager.adapter = MealCardAdapter(fragmentsList, this)
    }

    private fun getMealDetails(): Meal {
        val url = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId")

        val json = with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            inputStream.bufferedReader().use {
                it.lines().reduce { acc, string -> acc + string }
            }
        }

        return Json { ignoreUnknownKeys = true }.decodeFromString<MealList>(json.get()).meals[0]
    }

    private fun addToListWithCondition(ingredient: String?, measure: String?) {
        if (ingredient != null && ingredient != "" && measure != null && measure != "") {
            ingredientList.add(ingredient)
            measureList.add(measure)
        }
    }

    //TODO: помолиться x2.
    private fun postToList(meal: Meal) {
        addToListWithCondition(meal.strIngredient1, meal.strMeasure1)
        addToListWithCondition(meal.strIngredient2, meal.strMeasure2)
        addToListWithCondition(meal.strIngredient3, meal.strMeasure3)
        addToListWithCondition(meal.strIngredient4, meal.strMeasure4)
        addToListWithCondition(meal.strIngredient5, meal.strMeasure5)
        addToListWithCondition(meal.strIngredient6, meal.strMeasure6)
        addToListWithCondition(meal.strIngredient7, meal.strMeasure7)
        addToListWithCondition(meal.strIngredient8, meal.strMeasure8)
        addToListWithCondition(meal.strIngredient9, meal.strMeasure9)
        addToListWithCondition(meal.strIngredient10, meal.strMeasure10)
        addToListWithCondition(meal.strIngredient11, meal.strMeasure11)
        addToListWithCondition(meal.strIngredient12, meal.strMeasure12)
        addToListWithCondition(meal.strIngredient13, meal.strMeasure13)
        addToListWithCondition(meal.strIngredient14, meal.strMeasure14)
        addToListWithCondition(meal.strIngredient15, meal.strMeasure15)
        addToListWithCondition(meal.strIngredient16, meal.strMeasure16)
        addToListWithCondition(meal.strIngredient17, meal.strMeasure17)
        addToListWithCondition(meal.strIngredient18, meal.strMeasure18)
        addToListWithCondition(meal.strIngredient19, meal.strMeasure19)
        addToListWithCondition(meal.strIngredient20, meal.strMeasure20)
    }

    private fun setFragmentsToList(meal: Meal) {
        val titleFragment = MealCardTitleFragment(meal.strMeal, meal.strMealThumb)
        val ingredientsFragment = MealCardIngredientsFragment(
            ingredientList, measureList, this
        )
        val recipeFragment = MealCardRecipeFragment(
            StringFormater.splitByNewStringSymbol(meal.strInstructions!!), this
        )

        fragmentsList.add(titleFragment)
        fragmentsList.add(ingredientsFragment)
        fragmentsList.add(recipeFragment)

        println(meal.strYoutube)

        if (meal.strYoutube != null && meal.strYoutube != "") {
            val linksFragment = MealCardLinksFragment(meal.strYoutube, this)
            fragmentsList.add(linksFragment)
        }
    }

    private fun updateUI() {
        runOnUiThread {
            val viewPager = findViewById<ViewPager2>(R.id.meal_card_view_pager)
            viewPager.adapter?.notifyDataSetChanged()

            val tabsView = findViewById<CircleIndicator3>(R.id.meal_card_tabs)
            tabsView.setViewPager(viewPager)
        }
    }
}