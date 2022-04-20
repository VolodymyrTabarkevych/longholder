package com.traday.longholder.extensions

import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View.getDrawableCompat(
    @DrawableRes drawableResId: Int,
    theme: Resources.Theme? = null
): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableResId, theme)
}

fun View.getColorCompat(@ColorRes colorResId: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(resources, colorResId, theme)
}

fun View.getFontCompat(@FontRes fontResId: Int): Typeface? {
    return context?.let { ResourcesCompat.getFont(it, fontResId) }
}