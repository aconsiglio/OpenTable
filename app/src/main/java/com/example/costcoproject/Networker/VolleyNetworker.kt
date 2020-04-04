package com.example.costcoproject.Networker

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject

class VolleyNetworker {
    companion object {
        fun retrieveRestaurants(
            context: Context,
            city: String,
            onSuccess: (FindRestaurantsResponse) -> Unit,
            onFailure: (VolleyError) -> Unit
        ) {
            val queue = Volley.newRequestQueue(context)
            val url = "https://opentable.herokuapp.com/api/restaurants?city=${city}"
            val postRequest = object : StringRequest(
                Method.GET, url,
                Response.Listener<String> { response ->
                    val findRestaurantsResponse = Gson().fromJson(response, FindRestaurantsResponse::class.java)
                    //val restaurantEntry = Gson().fromJson(findRestaurantsResponse.restaurants[0] as LinkedTreeMap, RestaurantEntry::class.java)
                   // Log.d("TestTest", "$restaurantEntry")
                    onSuccess(findRestaurantsResponse)
                },
                Response.ErrorListener { volleyError ->
                    onFailure(volleyError)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Content-Type"] = "application/json"
                    params["Accept"] = "application/json"
                    return params
                }

            }.apply {
                retryPolicy = DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            }
            queue.add(postRequest)
        }
    }
}

data class FindRestaurantsResponse(val total_entries: Int, val per_page: Int, val current_page: Int, val restaurants: List<Any>)

data class RestaurantEntry(val id: Int, val name: String, val address: String, val city: String, val state: String, val area: String, val postal_code: Int, val country: String,
                           val phone: Int, val lat: Double, val lng: Double, val price: Double, val reserve_url: String, val mobile_reserve: String, val image_url: String)
