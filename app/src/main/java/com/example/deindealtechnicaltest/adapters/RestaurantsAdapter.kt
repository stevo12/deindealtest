package com.example.deindealtechnicaltest.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.deindealtechnicaltest.R
import com.example.deindealtechnicaltest.backend.RestaurantsObject
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.restaurant_element.view.*

class RestaurantsAdapter(
    private val restaurantsList: MutableList<RestaurantsObject>,
    private val onClick: (RestaurantsObject) -> Unit
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_element, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun getItemCount(): Int = restaurantsList.size

    override fun onBindViewHolder(holderRestaurants: RestaurantsViewHolder, position: Int) {
        holderRestaurants.bind(restaurantsList[position], onClick)

    }

    fun refreshRestaurantsData(restaurants: MutableList<RestaurantsObject>) {
        restaurantsList.clear()
        restaurantsList.addAll(restaurants)
        notifyDataSetChanged()
    }

    inner class RestaurantsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: RestaurantsObject, onClickAction: (RestaurantsObject) -> Unit) {
            itemView.restaurantTitle.text = item.title
            itemView.restaurantSubTitle.text = item.subtitle

            Picasso.get()
                .load(Uri.parse(item.imageUrl))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.restaurantIcon)

            containerView.setOnClickListener {
                onClickAction(item)
            }

            if (item.isGone) {
                containerView.isGone = true
            } else {
                containerView.isVisible = true
            }
        }
    }
}