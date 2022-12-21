package com.example.whoopty

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whoopty.utils.ShaderFactory

class MealCardIngredientsFragment(
    private val ingredientList: List<String>,
    private val measureList: List<String>,
    private val activityContext: Context
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.meal_card_ingredients_item, container, false)

        val titleView = view.findViewById<TextView>(R.id.meal_card_ingredients_title)
        titleView.paint.shader = ShaderFactory.createRedGradientShader(activityContext)

        val recyclerView = view.findViewById<RecyclerView>(R.id.meal_card_ingredients_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(activityContext, 2)
        recyclerView.adapter = MealCardIngredientsAdapter(ingredientList, measureList, activityContext)

        return view
    }
}