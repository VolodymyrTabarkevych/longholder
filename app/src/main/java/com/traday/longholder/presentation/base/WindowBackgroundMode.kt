package com.traday.longholder.presentation.base

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.traday.longholder.R
import com.traday.longholder.extensions.getColorCompat


sealed class WindowBackgroundMode {

    object Primary : WindowBackgroundMode() {

        override fun onFragmentResumed(fragment: Fragment, tabBarMode: TabBarMode) {
            fragment.setNavigationBarColor(R.color.white)
            fragment.setWindowBackgroundColor(R.color.white)
        }
    }

    object Secondary : WindowBackgroundMode() {

        override fun onFragmentResumed(fragment: Fragment, tabBarMode: TabBarMode) {
            val navigationBarColor =
                if (tabBarMode == TabBarMode.VISIBLE) R.color.white else R.color.athens_gray

            fragment.setNavigationBarColor(navigationBarColor)
            fragment.setWindowBackgroundColor(R.color.athens_gray)
        }
    }

    companion object {

        private fun Fragment.setWindowBackgroundColor(@ColorRes colorResId: Int) {
            requireActivity().window.decorView.setBackgroundColor(getColorCompat(colorResId))
        }

        private fun Fragment.setNavigationBarColor(@ColorRes colorResId: Int) {
            requireActivity().window.navigationBarColor = getColorCompat(colorResId)
        }
    }

    abstract fun onFragmentResumed(fragment: Fragment, tabBarMode: TabBarMode)
}