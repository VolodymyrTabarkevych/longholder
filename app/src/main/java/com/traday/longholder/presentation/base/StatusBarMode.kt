package com.traday.longholder.presentation.base

import androidx.fragment.app.Fragment
import com.traday.longholder.R

sealed class StatusBarMode {

    object Primary : StatusBarMode() {

        override fun onFragmentResumed(fragment: Fragment) {
            with(fragment.requireActivity().window) {
                statusBarColor = fragment.resources.getColor(R.color.white, null)
            }
        }
    }

    object Secondary : StatusBarMode() {

        override fun onFragmentResumed(fragment: Fragment) {
            fragment.requireActivity().window.apply {
                statusBarColor = fragment.resources.getColor(R.color.athens_gray, null)
            }
        }
    }

    abstract fun onFragmentResumed(fragment: Fragment)
}