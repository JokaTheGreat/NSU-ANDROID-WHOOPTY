package com.example.whoopty.categorylist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.whoopty.R
import com.example.whoopty.models.Category
import com.example.whoopty.models.CategoryList
import com.example.whoopty.utils.StringFormatter
import kotlinx.serialization.decodeFromString
import me.relex.circleindicator.CircleIndicator3
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class CategoryListActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imageUrlList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list)

        workWithApi()

        val viewPager = findViewById<ViewPager2>(R.id.category_list_view_pager)
        viewPager.adapter = CategoryListAdapter(titleList, descriptionList, imageUrlList, this)
    }

    private fun workWithApi() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/categories.php")
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

                val categories = getCategoryList(response.body!!.string())
                postToList(categories)
                updateUI()
            }
        })
    }

    private fun addToList(title: String, description: String, image: String) {
        titleList.add(title)
        descriptionList.add(description)
        imageUrlList.add(image)
    }

    private fun postToList(categories: Array<Category>) {
        categories.forEach {
            addToList(
                it.strCategory,
                StringFormatter.formatDescription(it.strCategoryDescription),
                it.strCategoryThumb
            )
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        runOnUiThread {
            val viewPager = findViewById<ViewPager2>(R.id.category_list_view_pager)
            viewPager.adapter?.notifyDataSetChanged()

            val indicator = findViewById<CircleIndicator3>(R.id.category_list_tabs)
            indicator.setViewPager(viewPager)
        }
    }

    private fun getCategoryList(json: String): Array<Category> =
        Json.decodeFromString<CategoryList>(json).categories
}