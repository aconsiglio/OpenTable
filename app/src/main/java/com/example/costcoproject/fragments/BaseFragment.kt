package com.example.costcoproject.fragments

import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseFragment: androidx.fragment.app.Fragment() {
    private lateinit var uiJob: Job
    val uiScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + uiJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiJob = Job()
    }

    override fun onDestroy() {
        uiJob.cancel()
        super.onDestroy()
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}