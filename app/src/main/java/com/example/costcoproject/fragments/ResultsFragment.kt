package com.example.costcoproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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

    interface OnRestaurantSelectedListener {
        fun onRestaurantSelected(id: Int)
    }

    private var findRestaurantsResponse: FindRestaurantsResponse? = null

    private var listener: OnRestaurantSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findRestaurantsResponse = CostcoApplication.instance.findRestaurantsResponse
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnRestaurantSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_results, container, false)

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val jsonElement = gson.toJsonTree(findRestaurantsResponse!!.restaurants)
        val restaurants =  Gson().fromJson(jsonElement, Array<RestaurantEntry>::class.java).toMutableList()
        CostcoApplication.instance.restaurants = restaurants
        uiScope.launch {
            resultsRecyclerView?.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = RestaurantAdapter(restaurants)
                (adapter as RestaurantAdapter).onItemClick = {
                    listener?.onRestaurantSelected(it)
                }
            }
        }
    }

    companion object {

        const val NAME = "results-fragment"

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}