package com.example.costcoproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.costcoproject.Networker.RestaurantEntry
import com.example.costcoproject.R
import kotlinx.android.synthetic.main.adapter_restaurant.view.*

class RestaurantAdapter(private var items: MutableList<RestaurantEntry>): RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        val element = items[position]
        return (element.name.hashCode() + element.image_url.hashCode()).toLong()
    }
}

class RestaurantViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(entry: RestaurantEntry) {
        view.restaurantName.text = entry.name
    }
}