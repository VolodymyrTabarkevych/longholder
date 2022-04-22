package com.traday.longholder.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.IdRes
import com.google.android.material.textfield.TextInputLayout

fun TextView.setTextWatcher(
    afterTextChanged: ((Editable?) -> Unit)? = null,
    beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(s, start, before, count)
        }
    })
}

fun TextInputLayout.setupWithErrorClearListener() {
    this.editText?.let { editText ->
        editText.setOnTextChangedListener(object : OnTextChangedListener {
            override fun onTextChanged(viewId: Int?) {
                if (editText.hasFocus() || endIconMode == TextInputLayout.END_ICON_CLEAR_TEXT) {
                    this@setupWithErrorClearListener.isErrorEnabled = false
                }
            }
        })
    }
}

fun TextView.setOnTextChangedListener(onTextChangedListener: OnTextChangedListener) {
    this.setTextWatcher(onTextChanged = { _, _, _, _ ->
        onTextChangedListener.onTextChanged(id)
    })
}

interface OnTextChangedListener {

    fun onTextChanged(@IdRes viewId: Int? = null)
}