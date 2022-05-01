package com.traday.longholder.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadWithGlide(
    url: String?,
    placeholderResId: Int? = null,
    cornerRadius: Int? = null,
    scaleType: BitmapTransformation? = null
) {
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .also { glideRequest ->
            placeholderResId?.let {
                glideRequest.placeholder(it)
            } ?: run {
                glideRequest.placeholder(null)
            }
            scaleType?.let {
                glideRequest.transform(scaleType)
            }
            cornerRadius?.let {
                glideRequest.transform(RoundedCorners(it))
            }
            glideRequest.into(this)
        }
}