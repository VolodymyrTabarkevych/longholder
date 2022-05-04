package com.traday.longholder.utils

import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import com.traday.longholder.R
import com.traday.longholder.extensions.toPx

class ViewUtils {
    companion object {

        fun accessibleTouchTarget(vararg views: View) {
            for (view in views) {
                view.post {
                    val delegateArea = Rect()
                    view.getHitRect(delegateArea)

                    val widgetHeight: Int =
                        (view.resources.getDimension(R.dimen.widget_size_6) / view.resources.displayMetrics.density).toInt()
                    val accessibilityMin = widgetHeight.toPx()

                    val height = delegateArea.bottom - delegateArea.top
                    if (accessibilityMin > height) {
                        val addition = ((accessibilityMin - height) / 2) + 1
                        delegateArea.top -= addition
                        delegateArea.bottom += addition
                    }

                    val width = delegateArea.right - delegateArea.left
                    if (accessibilityMin > width) {
                        val addition = ((accessibilityMin - width) / 2) + 1
                        delegateArea.left -= addition
                        delegateArea.right += addition
                    }

                    val parentView = view.parent as? View
                    parentView?.let { it ->
                        val touchDelegate: TouchDelegate? = it.touchDelegate
                        if (touchDelegate == null || touchDelegate !is TouchDelegateComposite) {
                            val touchDelegateComposite = TouchDelegateComposite(it)
                            touchDelegateComposite.addDelegate(TouchDelegate(delegateArea, view))
                            it.touchDelegate = touchDelegateComposite
                        } else {
                            touchDelegate.addDelegate(TouchDelegate(delegateArea, view))
                        }
                    }
                }
            }
        }
    }
}

class TouchDelegateComposite(view: View) : TouchDelegate(EMPTY_RECT, view) {

    private val delegates: MutableList<TouchDelegate> = arrayListOf()

    fun addDelegate(delegate: TouchDelegate) {
        delegates.add(delegate)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var res = false
        for (delegate in delegates) {
            event.setLocation(event.x, event.y)
            res = delegate.onTouchEvent(event) || res
        }
        return res
    }

    companion object {

        private val EMPTY_RECT: Rect = Rect()
    }
}
