package com.example.deindealtechnicaltest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deindealtechnicaltest.R
import com.example.deindealtechnicaltest.adapters.DetailsAdapter
import com.example.deindealtechnicaltest.backend.DeinDealService
import com.example.deindealtechnicaltest.backend.MenuObject
import com.example.deindealtechnicaltest.backend.RestaurantsApi
import com.example.deindealtechnicaltest.ui.MainActivity.Companion.RESTAURANT_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var deinDealService: DeinDealService
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        setSupportActionBar(toolbar)
        initRecyclerView()

        if (intent != null) {
            restaurantId = intent.getIntExtra(RESTAURANT_ID, 0)
            fetchRestaurantDetails(restaurantId)
            title = "Restaurant $restaurantId"
        }

    }

    private fun initRecyclerView() {
        mealsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@DetailsActivity, 2)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun fetchRestaurantDetails(id: Int) {
        compositeDisposable = CompositeDisposable()
        deinDealService = RestaurantsApi.retrofit.create(DeinDealService::class.java)
        compositeDisposable.addAll(deinDealService.getRestaurantDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { meals ->
                displayMeals(meals.menu)
                run {
                    refreshData(meals.menu)
                }
            })
    }


    private fun displayMeals(meals: MutableList<MenuObject>) {
        detailsAdapter = DetailsAdapter(meals)
        mealsRecyclerView.adapter = detailsAdapter
    }

    private fun refreshData(meals: MutableList<MenuObject>) {
        refreshLayout.setOnRefreshListener {
            fetchRestaurantDetails(restaurantId)
            detailsAdapter.refreshData(meals)
            refreshLayout.isRefreshing = false
        }
    }
}

