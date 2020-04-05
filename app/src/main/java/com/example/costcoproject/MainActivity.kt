package com.example.costcoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.costcoproject.Networker.VolleyNetworker
import com.example.costcoproject.fragments.SearchFragment
import kotlinx.android.synthetic.main.search_bar_view.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.costcoproject.fragments.ErrorFragment
import com.example.costcoproject.fragments.RestaurantDetailedFragment
import com.example.costcoproject.fragments.ResultsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrowView?.setOnClickListener {
            val input =  (editText as EditText).text.trim().toString()
            VolleyNetworker.retrieveRestaurants(applicationContext, input, { response ->
                val appState = applicationContext as CostcoApplication
                appState.findRestaurantsResponse = response
                switchFragment(FragmentNavigation.RESULTS)
/*                response.restaurants.forEach {
                    Log.d("TestTest", "$it")
                }*/
            }, { volleyError ->
                Log.d("TestTest", "$volleyError")
            })
        }
    }

    override fun onResume() {
        super.onResume()
        switchFragment(FragmentNavigation.SEARCH)
    }

    private fun switchFragment(navigation: FragmentNavigation) {
        val transaction = supportFragmentManager.beginTransaction()
        when (navigation) {
            FragmentNavigation.SEARCH -> {
                transaction.replace(R.id.container, SearchFragment.newInstance())
                transaction.addToBackStack(SearchFragment.NAME)
            }
            FragmentNavigation.RESULTS -> {
                transaction.replace(R.id.container, ResultsFragment.newInstance())
                transaction.addToBackStack(ResultsFragment.NAME)
            }
            FragmentNavigation.DETAILED -> {
                transaction.replace(R.id.container, RestaurantDetailedFragment.newInstance())
                transaction.addToBackStack(RestaurantDetailedFragment.NAME)
            }
            FragmentNavigation.ERROR -> {
                transaction.replace(R.id.container, ErrorFragment.newInstance())
                transaction.addToBackStack(ErrorFragment.NAME)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}

enum class FragmentNavigation {
    SEARCH,
    RESULTS,
    ERROR,
    DETAILED
}
