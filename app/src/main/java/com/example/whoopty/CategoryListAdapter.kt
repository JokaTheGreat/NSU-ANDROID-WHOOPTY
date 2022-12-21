package com.example.whoopty

import android.content.Context
import android.content.Intent
import android.graphics.LinearGradient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whoopty.utils.ShaderFactory

class CategoryListAdapter(
    private var titleList: List<String>,
    private var descriptionList: List<String>,
    private var imageUrlList: List<String>,
    private var activityContext: Context
) : RecyclerView.Adapter<CategoryListAdapter.CategoryItemViewHolder>() {
    private var titleShader: LinearGradient? = null

    init {
        titleShader = ShaderFactory.createRedGradientShader(activityContext)
    }

    inner class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: ConstraintLayout = itemView.findViewById(R.id.category_item)
        val itemTitle: TextView = itemView.findViewById(R.id.category_item_title)
        val itemDescription: TextView = itemView.findViewById(R.id.category_item_description)
        val itemImage: ImageView = itemView.findViewById(R.id.category_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {

        holder.item.setOnClickListener(ClickListener(titleList[position]))

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
        } //TODO: может стоит грузить картинки до попадания в адаптер?
    }

    inner class ClickListener(private val categoryTitle: String) : View.OnClickListener {
        override fun onClick(p0: View?) {
            val intent = Intent(activityContext, MealListActivity::class.java).apply {
                putExtra("categoryTitle", categoryTitle)
            }

            startActivity(
                activityContext,
                intent,
                null
            )//TODO: почему мы дважды пробрасываем контекст?
        }
    }

    override fun getItemCount(): Int = titleList.size
}