package com.example.deindealtechnicaltest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deindealtechnicaltest.R
import com.example.deindealtechnicaltest.adapters.FiltersAdapters
import com.example.deindealtechnicaltest.adapters.RestaurantsAdapter
import com.example.deindealtechnicaltest.backend.DeinDealService
import com.example.deindealtechnicaltest.backend.FiltersObject
import com.example.deindealtechnicaltest.backend.RestaurantsApi
import com.example.deindealtechnicaltest.backend.RestaurantsObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var restaurantsAdapter: RestaurantsAdapter
    private lateinit var filtersAdapter: FiltersAdapters
    private lateinit var deinDealService: DeinDealService
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = "Restaurants"
        initAdapters()
        fetchRestaurantsList()
    }

    private fun initAdapters() {
        restaurantsListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
        }
        filtersRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun fetchRestaurantsList() {
        compositeDisposable = CompositeDisposable()
        deinDealService = RestaurantsApi.retrofit.create(DeinDealService::class.java)
        compositeDisposable.add(deinDealService.getRestaurantsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { restaurants ->
                run {
                    displayFilters(restaurants.filters, restaurants.restaurants)
                    displayRestaurants(restaurants.restaurants)
                    refreshData(restaurants.restaurants)

                }
            })

    }

    private fun displayRestaurants(restaurants: MutableList<RestaurantsObject>) {
        restaurantsAdapter = RestaurantsAdapter(restaurants) { restaurant ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(RESTAURANT_ID, restaurant.id)
            startActivity(intent)

        }
        restaurantsListRecyclerView.adapter = restaurantsAdapter
    }

    private fun displayFilters(
        filters: List<FiltersObject>,
        restaurants: MutableList<RestaurantsObject>
    ) {
        filtersAdapter = FiltersAdapters(filters, this) { filter ->
            if (filter.isSelected) {
                filters.forEach {
                    it.isSelected = false
                    restaurants.forEach { restaurant ->
                        restaurant.isGone = !restaurant.filters.contains(filter.id)
                    }
                }
                restaurants.forEach { restaurant ->
                    restaurant.isGone = false
                }
                filter.isSelected = false
            } else {
                filters.forEach { it.isSelected = false }
                restaurants.forEach { restaurant ->
                    restaurant.isGone = !restaurant.filters.contains(filter.id)
                }
                filter.isSelected = true
            }
            restaurantsAdapter.notifyDataSetChanged()
            filtersAdapter.notifyDataSetChanged()
        }
        filtersRecyclerView.adapter = filtersAdapter
    }

    private fun refreshData(restaurants: MutableList<RestaurantsObject>) {
        swipeRefreshLayout.setOnRefreshListener {
            fetchRestaurantsList()
            restaurantsAdapter.refreshRestaurantsData(restaurants)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {

        const val RESTAURANT_ID = "id"
    }

}