package com.example.costcoproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.costcoproject.CostcoApplication
import com.example.costcoproject.Networker.FindRestaurantsResponse
import com.example.costcoproject.Networker.RestaurantEntry
import com.example.costcoproject.R
import com.example.costcoproject.adapters.RestaurantAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.coroutines.launch

class ResultsFragment: BaseFragment() {

    private var findRestaurantsResponse: FindRestaurantsResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findRestaurantsResponse = CostcoApplication.instance.findRestaurantsResponse
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val jsonElement = gson.toJsonTree(findRestaurantsResponse!!.restaurants)
        val restaurants =  Gson().fromJson(jsonElement, Array<RestaurantEntry>::class.java).toMutableList()
        uiScope.launch {
            resultsRecyclerView?.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = RestaurantAdapter(restaurants)
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_results, container, false)

    companion object {

        const val NAME = "results-fragment"

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}