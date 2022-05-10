package com.traday.longholder.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
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

fun TextInputLayout.setupWithDefaultConfiguration(
    onStateChanged: () -> Unit = {},
    onActionDone: () -> Unit = {}
) {
    this.editText?.let { editText ->
        editText.setOnTextChangedListener(object : OnTextChangedListener {
            override fun onTextChanged(viewId: Int?) {
                if (editText.hasFocus() || endIconMode == TextInputLayout.END_ICON_CLEAR_TEXT) {
                    this@setupWithDefaultConfiguration.isErrorEnabled = false
                }
                onStateChanged.invoke()
            }
        })
        editText.setOnFocusChangeListener { _, _ ->
            onStateChanged.invoke()
        }
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onActionDone.invoke()
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener true
        }
    }
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

fun TextInputLayout.setErrorIfOnFocusAndNotEmpty(errorMsg: String?) {
    this.editText?.let { editText ->
        if (!editText.hasFocus() && editText.text.toString().isNotEmpty()) {
            this.error = errorMsg
        }
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