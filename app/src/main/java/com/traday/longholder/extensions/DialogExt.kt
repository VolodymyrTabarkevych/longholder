package com.traday.longholder.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.traday.longholder.R
import com.traday.longholder.databinding.DialogCommonBinding

fun Fragment.showDialog(
    title: String,
    message: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveButtonClicked: () -> Unit = {},
    onNegativeButtonClicked: () -> Unit = {},
    onCustomize: (DialogCommonBinding, AlertDialog) -> Unit = { _, _ -> }
) {
    val binding = DialogCommonBinding.inflate(LayoutInflater.from(requireContext()))
    val dialog = MaterialAlertDialogBuilder(requireContext())
        .setBackground(getDrawableCompat(R.drawable.background_transparent))
        .setView(binding.root)
        .create()

    with(binding) {
        tvDialogTitle.text = title
        message?.let { message ->
            tvDialogMessage.text = message
        } ?: run { tvDialogMessage.visibility = View.GONE }
        pbDialogPositiveButton.let {
            it.setText(positiveButtonText ?: getString(R.string.common_ok))
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
        dialog.show()
    }
}