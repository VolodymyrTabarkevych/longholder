package com.traday.longholder.extensions

import android.content.Context
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

fun Fragment.getDrawableCompat(
    @DrawableRes drawableResId: Int,
    theme: Resources.Theme? = null
): Drawable? {
    return context?.getDrawableCompat(drawableResId, theme)
}

fun Fragment.getColorCompat(@ColorRes colorResId: Int, theme: Resources.Theme? = null): Int {
    return requireContext().getColorCompat(colorResId, theme)
}

fun Fragment.getFontCompat(@FontRes fontResId: Int): Typeface? {
    return requireContext().getFontCompat(fontResId)
}

fun View.getDrawableCompat(
    @DrawableRes drawableResId: Int,
    theme: Resources.Theme? = null
): Drawable? {
    return context.getDrawableCompat(drawableResId, theme)
}

fun View.getColorCompat(@ColorRes colorResId: Int, theme: Resources.Theme? = null): Int {
    return context.getColorCompat(colorResId, theme)
}

fun View.getFontCompat(@FontRes fontResId: Int): Typeface? {
    return context.getFontCompat(fontResId)
}

fun Context.getDrawableCompat(
    @DrawableRes drawableResId: Int,
    theme: Resources.Theme? = null
): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableResId, theme)
}

fun Context.getColorCompat(@ColorRes colorResId: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(resources, colorResId, theme)
}

fun Context.getFontCompat(@FontRes fontResId: Int): Typeface? {
    return ResourcesCompat.getFont(this, fontResId)
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