package com.example.costcoproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.costcoproject.R

class LoadingFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_loading, container, false)

    companion object {
        const val NAME = "loading-fragment"

        @JvmStatic
        fun newInstance() = LoadingFragment()
    }
}
