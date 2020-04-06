package com.example.costcoproject

import androidx.multidex.MultiDexApplication
import com.example.costcoproject.Networker.FindRestaurantsResponse
import com.example.costcoproject.Networker.RestaurantEntry

class CostcoApplication : MultiDexApplication() {
    var restaurants = mutableListOf<RestaurantEntry>()
    var findRestaurantsResponse: FindRestaurantsResponse? = null

    init {
        instance = this
    }

    companion object {
        lateinit var instance: CostcoApplication
    }
}