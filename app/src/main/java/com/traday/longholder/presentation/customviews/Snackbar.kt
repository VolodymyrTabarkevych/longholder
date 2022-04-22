package com.traday.longholder.presentation.customviews

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback
import com.traday.longholder.R


class Snackbar(
    parent: ViewGroup,
    content: SnackbarView
) : BaseTransientBottomBar<Snackbar>(parent, content, content) {

    init {
        view.let {
            it.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
            it.setPadding(0, 0, 0, 0)
        }
    }

    companion object {

        fun make(view: View): Snackbar {
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )
            return (Snackbar(
                parent, LayoutInflater.from(view.context).inflate(
                    R.layout.widget_snackbar,
                    parent,
                    false
                ) as SnackbarView
            )
                    )
        }
    }
}

class SnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val tvSnackbarMessage: TextView

    init {
        View.inflate(context, R.layout.view_snackbar, this)
        clipToPadding = false
        tvSnackbarMessage = findViewById(R.id.tvSnackbarMessage)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(tvSnackbarMessage, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(tvSnackbarMessage, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {}
}

private fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }
        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback
}