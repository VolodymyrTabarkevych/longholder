package com.traday.longholder.extensions

import android.graphics.PorterDuff
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

fun TextView.rightDrawable(@DrawableRes id: Int = 0, @ColorRes colorRes: Int = 0) {
    val drawable = getDrawableCompat(id)
    if (colorRes != 0) {
        val colorInt = getColorCompat(colorRes)
        drawable?.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
    }
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
}