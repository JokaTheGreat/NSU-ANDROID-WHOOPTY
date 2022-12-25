package com.example.whoopty.mealcard

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.whoopty.R
import com.example.whoopty.models.Meal
import com.example.whoopty.models.MealList
import com.example.whoopty.utils.StringFormatter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.relex.circleindicator.CircleIndicator3
import java.net.HttpURLConnection
import java.net.URL

class MealCardActivity : AppCompatActivity() {
    private var mealId: Int? = null
    private var meal = mutableListOf<Meal>()
    private var ingredientList = mutableListOf<String>()
    private var measureList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_card)

        mealId = intent.getIntExtra("mealId", -1)

        Thread {
            meal.addAll(getMealDetails())
            postToList()
            updateUI()
        }.start()

        val backToListView = findViewById<ImageView>(R.id.meal_card_back_to_meals)
        backToListView.setOnClickListener { onBackPressed() }

        val viewPager = findViewById<ViewPager2>(R.id.meal_card_view_pager)
        viewPager.adapter = MealCardAdapter(meal, ingredientList, measureList, this)
    }

    private fun getMealDetails(): Array<Meal> {
        val url = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId")

        val json = with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            inputStream.bufferedReader().use {
                it.lines().reduce { acc, string -> acc + string }
            }
        }

        return Json { ignoreUnknownKeys = true }.decodeFromString<MealList>(json.get()).meals
    }

    private fun addToListWithCondition(ingredient: String?, measure: String?) {
        if (ingredient != null && ingredient != "" && measure != null && measure != "") {
            ingredientList.add(ingredient)
            measureList.add(measure)
        }
    }

    //TODO: помолиться x2.
    private fun postToList() {
        addToListWithCondition(meal[0].strIngredient1, meal[0].strMeasure1)
        addToListWithCondition(meal[0].strIngredient2, meal[0].strMeasure2)
        addToListWithCondition(meal[0].strIngredient3, meal[0].strMeasure3)
        addToListWithCondition(meal[0].strIngredient4, meal[0].strMeasure4)
        addToListWithCondition(meal[0].strIngredient5, meal[0].strMeasure5)
        addToListWithCondition(meal[0].strIngredient6, meal[0].strMeasure6)
        addToListWithCondition(meal[0].strIngredient7, meal[0].strMeasure7)
        addToListWithCondition(meal[0].strIngredient8, meal[0].strMeasure8)
        addToListWithCondition(meal[0].strIngredient9, meal[0].strMeasure9)
        addToListWithCondition(meal[0].strIngredient10, meal[0].strMeasure10)
        addToListWithCondition(meal[0].strIngredient11, meal[0].strMeasure11)
        addToListWithCondition(meal[0].strIngredient12, meal[0].strMeasure12)
        addToListWithCondition(meal[0].strIngredient13, meal[0].strMeasure13)
        addToListWithCondition(meal[0].strIngredient14, meal[0].strMeasure14)
        addToListWithCondition(meal[0].strIngredient15, meal[0].strMeasure15)
        addToListWithCondition(meal[0].strIngredient16, meal[0].strMeasure16)
        addToListWithCondition(meal[0].strIngredient17, meal[0].strMeasure17)
        addToListWithCondition(meal[0].strIngredient18, meal[0].strMeasure18)
        addToListWithCondition(meal[0].strIngredient19, meal[0].strMeasure19)
        addToListWithCondition(meal[0].strIngredient20, meal[0].strMeasure20)
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