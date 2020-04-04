package com.example.costcoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.costcoproject.Networker.VolleyNetworker
import com.example.costcoproject.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateFragment()
        searchEditText?.setOnClickListener { editText ->
            val input = (editText as EditText).text.trim().toString()
            VolleyNetworker.retrieveRestaurants(applicationContext, input, { response ->
                response.restaurants.forEach {
                    Log.d("TestTest", "$it")
                }
            }, { volleyError ->
                Log.d("TestTest", "$volleyError")
            })
        }
    }


    private fun updateFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, SearchFragment.newInstance())
        transaction.addToBackStack(SearchFragment.NAME)
        transaction.commitAllowingStateLoss()
    }
}
