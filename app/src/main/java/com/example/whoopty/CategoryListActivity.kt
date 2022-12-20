package com.example.whoopty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.whoopty.models.Category
import com.example.whoopty.models.CategoryList
import com.example.whoopty.utils.StringFormater
import kotlinx.serialization.decodeFromString
import me.relex.circleindicator.CircleIndicator3
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.Json

class CategoryListActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imageUrlList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list)

        //TODO: делает запросы на каждом рекрейте активити, нормально??
        Thread {
            val categories = getCategoryList()
            postToList(categories)
            updateUI()
        }.start()

        val viewPager = findViewById<ViewPager2>(R.id.category_list_view_pager)
        viewPager.adapter = CategoryListAdapter(titleList, descriptionList, imageUrlList, this)
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
                StringFormater.formatDescription(it.strCategoryDescription),
                it.strCategoryThumb
            )
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

    private fun getCategoryList(): Array<Category> {
        val url = URL("https://www.themealdb.com/api/json/v1/1/categories.php")

        val json = with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            inputStream.bufferedReader().use {
                it.lines().reduce { acc, string -> acc + string }
            }
        }

        return Json.decodeFromString<CategoryList>(json.get()).categories
    }
}

/*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.*
*/
/*
class MyYoutubeListener : YouTubeThumbnailView.OnInitializedListener {
    override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
        p1?.setVideo("nMyBC9staMU")
    }

    override fun onInitializationFailure(
        p0: YouTubeThumbnailView?,
        p1: YouTubeInitializationResult?
    ) {
        println(p0)
        println(p1)
        println("failed initialization youtubeeee")
    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_card_links_item)

        findViewById<YouTubeThumbnailView>(R.id.youtube_thumbnail).initialize(DeveloperKey().DEVELOPER_KEY, MyYoutubeListener())
    }
}
*/