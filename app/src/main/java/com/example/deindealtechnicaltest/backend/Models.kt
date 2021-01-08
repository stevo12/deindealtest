package com.example.deindealtechnicaltest.backend

import com.google.gson.annotations.SerializedName

data class RestaurantsObject(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("filters") val filters: List<String>,
    var isGone: Boolean = false
)

data class FiltersObject(
    @SerializedName("id") val id: String,
    @SerializedName("label") val label: String,
    @SerializedName("icon") val iconUrl: String,
    var isSelected: Boolean = false

)

data class BaseResponseObject(
    @SerializedName("restaurants") val restaurants: MutableList<RestaurantsObject>,
    @SerializedName("filters") val filters: List<FiltersObject>
)

data class RestaurantObject(
    @SerializedName("title") val title: String,
    @SerializedName("menu") val menu: MutableList<MenuObject>
)

data class MenuObject(
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("price") val price: String
)