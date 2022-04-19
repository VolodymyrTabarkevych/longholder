package com.traday.longholder.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.traday.longholder.R

class AppTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        setErrorBackground(enabled)
    }

    fun setErrorBackground(enabled: Boolean) {
        editText?.background = if (enabled) ContextCompat.getDrawable(
            context,
            R.drawable.background_et_error
        ) else AppCompatResources.getDrawable(
            context,
            R.drawable.background_et
        )
    }
}