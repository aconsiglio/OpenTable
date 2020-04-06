package com.example.costcoproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.costcoproject.R
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment: BaseFragment() {

    private var error: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            error = it.getString(ARG_ERROR_KEY)
        }
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onStart() {
        super.onStart()
        errorText.text = context?.getString(R.string.error_occurred, error)
    }

    companion object {

        const val NAME = "error-fragment"
        private const val ARG_ERROR_KEY = "arg-error=key"

        @JvmStatic
        fun newInstance(errorStr: String? = null) = ErrorFragment().apply {
            arguments = Bundle().apply {
                errorStr?.let {
                    putString(ARG_ERROR_KEY, it)
                }
            }
        }
    }
}