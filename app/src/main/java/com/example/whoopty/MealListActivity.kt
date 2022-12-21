package com.example.whoopty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whoopty.models.Meal
import com.example.whoopty.models.MealList
import com.example.whoopty.utils.ShaderFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MealListActivity : AppCompatActivity() {
    private var categoryTitle: String? = null

    private var idList = mutableListOf<Int>()
    private var titleList = mutableListOf<String>()
    private var imageUrlList = mutableListOf<String>()
    private var ingredientTitleList = mutableListOf<String>() //TODO: реализовать ингредиенты

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_list)

        categoryTitle = intent.getStringExtra("categoryTitle")

        Thread {
            val meals = getMeals()
            postToList(meals)
            updateUI()
        }.start()

        val titleView = findViewById<TextView>(R.id.meal_list_title)
        titleView.text = "$categoryTitle meals"
        titleView.paint.shader = ShaderFactory.createRedGradientShader(this)

        val backToListView = findViewById<ImageView>(R.id.meal_list_back_to_categories)
        backToListView.setOnClickListener { onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.meal_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MealListAdapter(idList, titleList, imageUrlList, ingredientTitleList, this)
    }

    private fun getMeals(): Array<Meal> { //TODO: унифицировать запросы?
        val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c=$categoryTitle")

        val json = with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            inputStream.bufferedReader().use {
                it.lines().reduce { acc, string -> acc + string }
            }
        }

        return Json.decodeFromString<MealList>(json.get()).meals
    }

    private fun addToList(id: Int, title: String, image: String, ingredientTitle: String) {
        idList.add(id)
        titleList.add(title)
        imageUrlList.add(image)
        ingredientTitleList.add(ingredientTitle)
    }

    private fun postToList(meals: Array<Meal>) {
        meals.forEach {
            addToList(it.idMeal, it.strMeal, it.strMealThumb, categoryTitle!!)
        }
    }

    private fun updateUI() {
        runOnUiThread {
            val recyclerView = findViewById<RecyclerView>(R.id.meal_list_recycler_view)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}