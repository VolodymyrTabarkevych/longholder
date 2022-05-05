package com.traday.longholder.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.traday.longholder.R
import com.traday.longholder.databinding.DialogCommonBinding
import com.traday.longholder.extensions.getDrawableCompat
import com.traday.longholder.extensions.gone

interface AlertDialogProvider {

    val alertDialogContext: Context

    var isAlertDialogShowing: Boolean
}

fun AlertDialogProvider.showDialog(
    title: String,
    message: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveButtonClicked: () -> Unit = {},
    onNegativeButtonClicked: () -> Unit = {},
    onCustomize: (DialogCommonBinding, AlertDialog) -> Unit = { _, _ -> }
) {
    if (isAlertDialogShowing) return
    val binding = DialogCommonBinding.inflate(LayoutInflater.from(alertDialogContext))
    val dialog = MaterialAlertDialogBuilder(alertDialogContext)
        .setBackground(alertDialogContext.getDrawableCompat(R.drawable.background_transparent))
        .setView(binding.root)
        .create()

    with(binding) {
        tvDialogTitle.text = title
        message?.let { message ->
            tvDialogMessage.text = message
        } ?: run { tvDialogMessage.visibility = View.GONE }
        pbDialogPositiveButton.let {
            it.setText(positiveButtonText ?: alertDialogContext.getString(R.string.common_ok))
            it.setOnClickListener {
                onPositiveButtonClicked.invoke()
                dialog.dismiss()
            }
        }
        pbDialogNegativeButton.let {
            val showButton = negativeButtonText != null
            if (showButton) {
                it.setText(negativeButtonText)
                it.setOnClickListener {
                    onNegativeButtonClicked.invoke()
                    dialog.dismiss()
                }
            } else {
                it.gone()
            }
        }
        onCustomize(binding, dialog)
        dialog.setOnDismissListener { isAlertDialogShowing = false }
        dialog.show().also { isAlertDialogShowing = true }
    }
}