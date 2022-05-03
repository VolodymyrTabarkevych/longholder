package com.traday.longholder.presentation.common.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.traday.longholder.domain.model.Report

class ReportDiffCallback : DiffUtil.ItemCallback<Report>() {

    override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean =
        oldItem == newItem
}