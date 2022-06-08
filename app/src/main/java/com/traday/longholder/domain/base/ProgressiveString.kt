package com.traday.longholder.domain.base

import android.content.res.Resources
import androidx.annotation.StringRes

sealed class ProgressiveString {

    class Default(val text: String) : ProgressiveString()

    class Resource(@StringRes val stringRes: Int, vararg val args: Any) : ProgressiveString()

    fun toString(resources: Resources): String {
        return when (this) {
            is Default -> text
            is Resource -> resources.getString(stringRes, *args)
        }
    }
}
