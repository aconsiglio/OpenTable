package com.example.costcoproject.fragments

import android.os.Bundle

class ErrorFragment: BaseFragment() {
    companion object {

        const val NAME = "error-fragment"
        private const val ARG_ERROR_KEY = "arg-error=key"

        @JvmStatic
        fun newInstance(errorStr: String?) = ErrorFragment().apply {
            arguments = Bundle().apply {
                errorStr?.let {
                    putString(ARG_ERROR_KEY, it)
                }
            }
        }
    }
}