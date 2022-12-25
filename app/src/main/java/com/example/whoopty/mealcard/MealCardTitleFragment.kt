package com.example.whoopty.mealcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.whoopty.R

class MealCardTitleFragment(private val title: String, private val imageUrl: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.meal_card_title_item, container, false)

        val titleView = view.findViewById<TextView>(R.id.meal_card_title_title)
        titleView?.text = title

        val imageView = view.findViewById<ImageView>(R.id.meal_card_title_image)

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        return view
    }
}