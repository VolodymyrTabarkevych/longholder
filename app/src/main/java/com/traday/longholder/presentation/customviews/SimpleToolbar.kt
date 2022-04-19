package com.traday.longholder.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.traday.longholder.R
import com.traday.longholder.databinding.WidgetSimpleToolbarBinding
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.show

class SimpleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetSimpleToolbarBinding =
        WidgetSimpleToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.SimpleToolbar,
            defStyleAttr,
            0
        )
        val leftActionDrawable =
            arr.getResourceId(R.styleable.SimpleToolbar_toolbarLeftActionDrawable, 0)
        val rightActionText = arr.getString(R.styleable.SimpleToolbar_toolbarRightActionText)
        arr.recycle()

        setLeftActionDrawable(leftActionDrawable)
        setRightActionText(rightActionText)
    }

    fun setLeftActionDrawable(@DrawableRes drawableId: Int) {
        with(binding) {
            if (drawableId == 0) return
            ibSimpleToolbarLeftActionButton.show()
            ibSimpleToolbarLeftActionButton.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    drawableId,
                    context.theme
                )
            )
        }
    }

    fun setLeftActionOnCLickListener(l: OnClickListener) {
        binding.ibSimpleToolbarLeftActionButton.setOnClickListener(l)
    }

    fun setRightActionText(text: String?) {
        with(binding) {
            if (text == null) {
                tvSimpleToolbarRightAction.gone()
            } else {
                tvSimpleToolbarRightAction.show()
                tvSimpleToolbarRightAction.text = text
            }
        }
    }

    fun setRightActionOnClickListener(l: OnClickListener) {
        binding.tvSimpleToolbarRightAction.setOnClickListener(l)
    }

}