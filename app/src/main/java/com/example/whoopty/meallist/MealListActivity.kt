package com.example.whoopty.meallist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whoopty.R
import com.example.whoopty.models.Meal
import com.example.whoopty.models.MealList
import com.example.whoopty.utils.ShaderFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

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

        workWithApi()

        val titleView = findViewById<TextView>(R.id.meal_list_title)
        titleView.text = "$categoryTitle meals"
        titleView.paint.shader = ShaderFactory.createRedGradientShader(this)

        val backToListView = findViewById<ImageView>(R.id.meal_list_back_to_categories)
        backToListView.setOnClickListener { onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.meal_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            MealListAdapter(idList, titleList, imageUrlList, ingredientTitleList, this)
    }

    private fun workWithApi() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/filter.php?c=$categoryTitle")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showToast("Some troubles with internet connection \nPlease restart app")
                throw e
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    showToast("Something troubles with server \nPlease restart app")
                    throw IOException("bad response $response")
                }

                val meals = getMeals(response.body!!.string())
                postToList(meals)
                updateUI()
            }
        })
    }

    private fun getMeals(json: String): Array<Meal> = Json.decodeFromString<MealList>(json).meals

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

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        runOnUiThread {
            val recyclerView = findViewById<RecyclerView>(R.id.meal_list_recycler_view)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}