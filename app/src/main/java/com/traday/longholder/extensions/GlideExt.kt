package com.traday.longholder.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textfield.TextInputLayout

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

fun TextInputLayout.setStartIconWithGlide(url: String?) {
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                startIconDrawable = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                startIconDrawable = placeholder
            }
        })
}