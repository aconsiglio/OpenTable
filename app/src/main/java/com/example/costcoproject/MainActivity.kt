package com.example.costcoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.costcoproject.Networker.VolleyNetworker
import kotlinx.android.synthetic.main.search_bar_view.*

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.costcoproject.fragments.*
import java.util.*


class MainActivity : AppCompatActivity(), ResultsFragment.OnRestaurantSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrowView?.setOnClickListener {
            switchFragment(FragmentNavigation.Loading)
            val input = editText.text?.trim().toString().toLowerCase(Locale.US)
            val hint = editText.hint.toString().toLowerCase(Locale.US)
            VolleyNetworker.retrieveRestaurants(applicationContext, hint, input, { response ->
                val appState = applicationContext as CostcoApplication
                appState.findRestaurantsResponse = response
                switchFragment(FragmentNavigation.Results)
            }, { volleyError ->
                Log.d("TestTest", "$volleyError")
            })
        }
        switchFragment(FragmentNavigation.Home)
    }

    override fun onBackPressed() {
        switchFragment(FragmentNavigation.Back)
    }

    override fun onRestaurantSelected(id: Int) {
        switchFragment(FragmentNavigation.Detailed(id))
    }

    private fun switchFragment(navigation: FragmentNavigation) {
        val transaction = supportFragmentManager.beginTransaction()
        hideLoading()
        when (navigation) {
            FragmentNavigation.Back -> {
                if (supportFragmentManager.backStackEntryCount > 1)
                    supportFragmentManager.popBackStack()
            }
            FragmentNavigation.Home -> {
                transaction.replace(R.id.container, HomeFragment.newInstance())
                transaction.addToBackStack(HomeFragment.NAME)
            }
            FragmentNavigation.Results -> {
                transaction.replace(R.id.container, ResultsFragment.newInstance())
                transaction.addToBackStack(ResultsFragment.NAME)
            }
            is FragmentNavigation.Detailed -> {
                val name = RestaurantDetailedFragment.NAME
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.setCustomAnimations(0, 0, R.anim.enter_from_left, R.anim.exit_to_left)
                transaction.replace(R.id.container, RestaurantDetailedFragment.newInstance(navigation.id))
                transaction.addToBackStack(name)
            }
            FragmentNavigation.Error -> {
                transaction.replace(R.id.container, ErrorFragment.newInstance())
                transaction.addToBackStack(ErrorFragment.NAME)
            }
            FragmentNavigation.Loading -> {
                val name = LoadingFragment.NAME
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                transaction.add(R.id.container, LoadingFragment.newInstance(), name)
                transaction.addToBackStack(name)
            }
        }
        transaction.commit()
    }

    private fun hideLoading() {
        super.onActivityResult(0, 0, null)
        supportFragmentManager.popBackStack(LoadingFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

sealed class FragmentNavigation {
    object Results : FragmentNavigation()
    data class Detailed(val id: Int) : FragmentNavigation()
    object Error : FragmentNavigation()
    object Loading : FragmentNavigation()
    object Back : FragmentNavigation()
    object Home: FragmentNavigation()
}
