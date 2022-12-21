package com.example.whoopty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MealCardRecipeAdapter(
    private val recipeTextList: List<String>
) : RecyclerView.Adapter<MealCardRecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNumber = view.findViewById<TextView>(R.id.recipe_number)
        val itemText = view.findViewById<TextView>(R.id.recipe_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)

        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentNumber = position + 1
        holder.itemNumber.text = "$currentNumber."

        holder.itemText.text = recipeTextList[position]
    }

    override fun getItemCount(): Int = recipeTextList.size

}