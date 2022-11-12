package com.example.whoopty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter(
    private var title: List<String>,
    private var description: List<String>,
    private var image: List<Int>
) : RecyclerView.Adapter<CategoryListAdapter.Pager2ViewHolder> () {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemDescription: TextView = itemView.findViewById(R.id.description)
        val itemImage: ImageView = itemView.findViewById(R.id.image)

        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return Pager2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDescription.text = description[position]
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int = title.size
}