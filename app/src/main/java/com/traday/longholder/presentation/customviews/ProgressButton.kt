package com.traday.longholder.presentation.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.traday.longholder.R
import com.traday.longholder.databinding.WidgetProgressButtonBinding
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.show

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetProgressButtonBinding =
        WidgetProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            defStyleAttr,
            0
        )

        val enabled = arr.getBoolean(R.styleable.ProgressButton_progressButtonEnabled, true)
        val buttonText = arr.getString(R.styleable.ProgressButton_progressButtonText)
        val loading = arr.getBoolean(R.styleable.ProgressButton_progressButtonLoading, false)
        val type = Type.values()[arr.getInt(R.styleable.ProgressButton_progressButtonType, 0)]
        val color = arr.getColorStateList(R.styleable.ProgressButton_progressButtonColor)
        val icon = arr.getResourceId(R.styleable.ProgressButton_pbIcon, 0)
        arr.recycle()

        isEnabled = enabled
        setText(buttonText)
        setLoading(loading)
        setType(type)
        setColor(color)
        setIcon(icon)
    }

    fun setIcon(@DrawableRes drawable: Int) {
        if (drawable != 0) {
            binding.tvProgressButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawable)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        with(binding) {
            flProgressButtonMain.isEnabled = enabled
            flProgressButtonDisabled.let { if (enabled) it.gone() else it.show() }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.flProgressButtonMain.setOnClickListener(l)
    }

    fun setClickListenerForDisabledState(l: OnClickListener) {
        binding.flProgressButtonDisabled.setOnClickListener(l)
    }

    fun setText(text: String?) {
        binding.tvProgressButton.text = text
    }

    fun setLoading(loading: Boolean) {
        with(binding) {
            flProgressButtonMain.isClickable = !loading
            if (loading) {
                tvProgressButton.gone()
                pbProgressButton.show()
            } else {
                tvProgressButton.show()
                pbProgressButton.gone()
            }
        }
    }

    fun setType(type: Type) {
        with(binding) {
            when (type) {
                Type.PRIMARY -> {
                    flProgressButtonMain.background =
                        ContextCompat.getDrawable(context, R.drawable.background_prbtn_primary)
                    TextViewCompat.setTextAppearance(
                        tvProgressButton,
                        R.style.TextAppearance_App_ProgressButton_Primary
                    )
                }
                Type.EMPTY_WITH_BORDER -> {
                    flProgressButtonMain.background =
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.background_prbtn_empty_with_stroke
                        )
                    TextViewCompat.setTextAppearance(
                        tvProgressButton,
                        R.style.TextAppearance_App_ProgressButton_EmptyWithStroke
                    )
                }
            }
        }
    }

    fun setColor(color: ColorStateList?) {
        binding.flProgressButtonMain.backgroundTintList = color
    }

    fun setDrawable(@DrawableRes drawable: Int) {
        with(binding) {
            flProgressButtonMain.backgroundTintList = null
            flProgressButtonMain.background = ContextCompat.getDrawable(context, drawable)
        }
    }

    enum class Type {
        PRIMARY,
        EMPTY_WITH_BORDER
    }
}