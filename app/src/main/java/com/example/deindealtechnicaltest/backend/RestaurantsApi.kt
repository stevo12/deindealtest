package com.example.deindealtechnicaltest.backend

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestaurantsApi {

    var retrofit: Retrofit
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var deinDealService: DeinDealService
    const val BASE_URL = "https://cpalasanu.github.io"
    val restaurantList: MutableList<RestaurantsObject> = mutableListOf()

    init {

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}