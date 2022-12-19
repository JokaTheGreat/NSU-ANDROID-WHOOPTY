package com.example.whoopty

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryListAdapter(
    private var titleList: List<String>,
    private var descriptionList: List<String>,
    private var imageUrlList: List<String>,
    private var activityContext: Context
) : RecyclerView.Adapter<CategoryListAdapter.Pager2ViewHolder>() {
    private var titleShader: LinearGradient? = null

    init {
        titleShader = LinearGradient(
            0.0f,
            0.0f,
            0.0f,
            110.0f,

            intArrayOf(
                activityContext.getColor(R.color.red_gradient_start),
                activityContext.getColor(R.color.red_gradient_end)
            ),
            null,
            Shader.TileMode.CLAMP
        )
    }

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.category_item_title)
        val itemDescription: TextView = itemView.findViewById(R.id.category_item_description)
        val itemImage: ImageView = itemView.findViewById(R.id.category_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return Pager2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = titleList[position]

        holder.itemTitle.paint.shader = titleShader

        holder.itemDescription.text = descriptionList[position]

        if (titleList[position].equals("breakfast", true)) {
            holder.itemImage.setImageResource(R.drawable.breakfast)
        } else if (titleList[position].equals("goat", true)) {
            holder.itemImage.setImageResource(R.drawable.goat)
        } else {
            Glide.with(activityContext)
                .load(imageUrlList[position])
                .into(holder.itemImage)
        }
    }

    override fun getItemCount(): Int = titleList.size
}