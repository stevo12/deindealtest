package com.example.deindealtechnicaltest.backend

import com.example.deindealtechnicaltest.backend.RestaurantsApi.BASE_URL
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface DeinDealService {

    @GET("$BASE_URL/restaurants.json")
    fun getRestaurantsList() : Observable<BaseResponseObject>

    @GET("$BASE_URL/{id}/restaurant.json")
    fun getRestaurantDetails(@Path("id") id: Int) : Observable<RestaurantObject>
}