package com.example.deindealtechnicaltest.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deindealtechnicaltest.R
import com.example.deindealtechnicaltest.backend.MenuObject
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.meal_element.view.*

class DetailsAdapter(private val mealsList: MutableList<MenuObject>) :
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_element, parent, false)
        return DetailsViewHolder(view)
    }

    override fun getItemCount(): Int = mealsList.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(mealsList[position])
    }

    fun refreshData(meals: MutableList<MenuObject>) {
        mealsList.clear()
        mealsList.addAll(meals)
        notifyDataSetChanged()
    }

    inner class DetailsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: MenuObject) {
            itemView.mealName.text = item.title
            itemView.mealPrice.text = item.price
            Picasso.get()
                .load(Uri.parse(item.imageUrl))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.mealImage)
        }
    }
}