package com.example.whoopty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class MainActivity : AppCompatActivity() {
    private var titlesList = mutableListOf<String>()
    private var descriptionsList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list)

        postToList()

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = CategoryListAdapter(titlesList, descriptionsList, imagesList)

        val indicator = findViewById<CircleIndicator3>(R.id.view_pager_indicator)
        indicator.setViewPager(viewPager)
    }

    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descriptionsList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..14) {
            addToList("BEEF", "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]", R.drawable.beef)
        }
    }
}