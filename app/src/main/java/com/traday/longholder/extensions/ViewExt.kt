package com.traday.longholder.extensions

import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

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

fun Fragment.getDrawableCompat(
    @DrawableRes drawableResId: Int,
    theme: Resources.Theme? = null
): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableResId, theme)
}

fun Fragment.getColorCompat(@ColorRes colorResId: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(resources, colorResId, theme)
}

fun Fragment.getFontCompat(@FontRes fontResId: Int): Typeface? {
    return context?.let { ResourcesCompat.getFont(it, fontResId) }
}

fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.setMargins(
        left ?: params.leftMargin,
        top ?: params.topMargin,
        right ?: params.rightMargin,
        bottom ?: params.bottomMargin
    )
    layoutParams = params
}