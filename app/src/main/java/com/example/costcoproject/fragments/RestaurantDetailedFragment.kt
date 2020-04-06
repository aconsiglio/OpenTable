package com.example.costcoproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.costcoproject.CostcoApplication
import com.example.costcoproject.Networker.RestaurantEntry
import com.example.costcoproject.R
import kotlinx.android.synthetic.main.fragment_restaurant_detailed.*
import kotlinx.coroutines.launch

class RestaurantDetailedFragment: BaseFragment() {

    private var restaurantEntry: RestaurantEntry? = null
    private var id: Int? = null
    private var cost: Int? = null
        set(value) {
            when (value) {
                1 -> showOneStar()
                2 -> showTwoStar()
                3 -> showThreeStar()
                4 -> showFourStar()
                else -> showNoStars()
            }
            field = value
        }

    private var phone: String = ""
        set(value) {
            val str = value.split("x")
            field = str[0]
            phoneNumber.text = context?.getString(R.string.phone_concat, "+1", field)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            id = it.getInt(ARG_ID_KEY)
        }
        id?.let {
            restaurantEntry = CostcoApplication.instance.restaurants.filter{ restaurant -> restaurant.id == it  }.firstOrNull()
        }
        return inflater.inflate(R.layout.fragment_restaurant_detailed, container, false)
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            Glide.with(it)
                .load(restaurantEntry?.image_url)
                .apply(RequestOptions.centerCropTransform())
                .into(fullImage)
        }
        uiScope.launch {
            cost = restaurantEntry?.price?.toInt()
            name.text = restaurantEntry?.name
            address.text = context?.getString(R.string.address_concat , "${restaurantEntry?.address}", "${restaurantEntry?.city}", "${restaurantEntry?.state}", "${restaurantEntry?.postal_code}")
            phone = restaurantEntry?.phone ?: ""
        }
    }

    private fun showOneStar() {
        price1.visibility = View.VISIBLE
        price2.visibility = View.GONE
        price3.visibility = View.GONE
        price4.visibility = View.GONE
    }

    private fun showTwoStar() {
        price1.visibility = View.VISIBLE
        price2.visibility = View.VISIBLE
        price3.visibility = View.GONE
        price4.visibility = View.GONE
    }

    private fun showThreeStar() {
        price1.visibility = View.VISIBLE
        price2.visibility = View.VISIBLE
        price3.visibility = View.VISIBLE
        price4.visibility = View.GONE
    }

    private fun showFourStar() {
        price1.visibility = View.VISIBLE
        price2.visibility = View.VISIBLE
        price3.visibility = View.VISIBLE
        price4.visibility = View.VISIBLE
    }

    private fun showNoStars() {
        price1.visibility = View.GONE
        price2.visibility = View.GONE
        price3.visibility = View.GONE
        price4.visibility = View.GONE
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