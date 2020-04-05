package com.example.costcoproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.costcoproject.CostcoApplication
import com.example.costcoproject.Networker.RestaurantEntry
import com.example.costcoproject.R
import kotlinx.android.synthetic.main.fragment_restaurant_detailed.*
import kotlinx.coroutines.launch

class RestaurantDetailedFragment: BaseFragment() {

    private lateinit var restaurantEntry: RestaurantEntry
    private var id: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            id = it.getInt(ARG_ID_KEY)
        }
        id?.let {
            restaurantEntry = CostcoApplication.instance.restaurants.filter{ restaurant -> restaurant.id == it  }.first()
        }
        return inflater.inflate(R.layout.fragment_restaurant_detailed, container, false)
    }

    override fun onResume() {
        super.onResume()
        uiScope.launch {
            name.text = restaurantEntry.name
            address.text = restaurantEntry.address
            phoneNumber.text = restaurantEntry.phone
        }
    }

    companion object {

        const val NAME = "restaurant-detailed-fragment"
        private const val ARG_ID_KEY = "arg-id-key"

        @JvmStatic
        fun newInstance(id: Int? = null) = RestaurantDetailedFragment().apply {
            arguments = Bundle().apply {
                id?.let {
                    putInt(ARG_ID_KEY, it)
                }
            }
        }
    }
}