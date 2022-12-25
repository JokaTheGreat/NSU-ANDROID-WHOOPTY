package com.example.whoopty.mealcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whoopty.R

class MealCardIngredientsAdapter(
    private val ingredientList: List<String>,
    private val measureList: List<String>,
    private val activityContext: Context
) : RecyclerView.Adapter<MealCardIngredientsAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById<ImageView>(R.id.ingredient_image)
        val itemTitle = view.findViewById<TextView>(R.id.ingredient_title)
        val itemMeasure = view.findViewById<TextView>(R.id.ingredient_measure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)

        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.itemTitle.text = ingredientList[position]

        holder.itemMeasure.text = measureList[position]

        val ingredientTitle = ingredientList[position]
        Glide.with(activityContext)
            .load("https://www.themealdb.com/images/ingredients/$ingredientTitle.png")
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int = ingredientList.size

}