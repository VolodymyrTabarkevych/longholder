package com.traday.longholder.extensions

import android.view.View
import androidx.fragment.app.Fragment
import com.traday.longholder.MainActivity
import com.traday.longholder.presentation.base.BottomNavigationViewProvider
import com.traday.longholder.presentation.customviews.Snackbar

fun Fragment.showSnack(anchorView: View? = null) {
    view?.let {
        Snackbar.make(it)
            .also { snackbar -> snackbar.view.setOnClickListener { snackbar.dismiss() } }
            .setAnchorView(anchorView)
            .show()
    }
}

fun Fragment.showSnackOverBottomNavigationView() {
    val activity = activity as? MainActivity
        ?: throw IllegalStateException("Activity ($activity) must implement ${BottomNavigationViewProvider::class.java.name}")
    showSnack(activity.getBottomNavigationView())
}