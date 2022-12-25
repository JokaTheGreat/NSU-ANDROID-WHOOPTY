package com.example.whoopty.meallist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whoopty.mealcard.MealCardActivity
import com.example.whoopty.R

class MealListAdapter(
    private var idList: List<Int>,
    private var titleList: List<String>,
    private var imageUrlList: List<String>,
    private var ingredientTitleList: List<String>,
    private var activityContext: Context
) : RecyclerView.Adapter<MealListAdapter.MealItemViewHolder>() {

    inner class MealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: CardView = itemView.findViewById(R.id.meal_item)
        val itemTitle: TextView = itemView.findViewById(R.id.meal_item_title)
        val itemImage: ImageView = itemView.findViewById(R.id.meal_item_image)
        val itemIngredient: FragmentContainerView =
            itemView.findViewById(R.id.meal_item_third_ingredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)

        return MealItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealItemViewHolder, position: Int) {

        holder.item.setOnClickListener(ClickListener(idList[position]))

        holder.itemTitle.text = titleList[position]

        Glide.with(activityContext)
            .load(imageUrlList[position])
            .circleCrop()
            .into(holder.itemImage)
    }

    inner class ClickListener(private val mealId: Int) : View.OnClickListener {
        override fun onClick(p0: View?) {
            val intent = Intent(activityContext, MealCardActivity::class.java).apply {
                putExtra("mealId", mealId)
            }

            startActivity(
                activityContext,
                intent,
                null
            )
        }
    }


    override fun getItemCount(): Int = idList.size
}