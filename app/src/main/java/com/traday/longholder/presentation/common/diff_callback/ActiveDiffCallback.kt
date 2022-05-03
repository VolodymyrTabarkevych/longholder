package com.traday.longholder.presentation.common.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.traday.longholder.domain.model.Active

class ActiveDiffCallback : DiffUtil.ItemCallback<Active>() {

    override fun areItemsTheSame(oldItem: Active, newItem: Active): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Active, newItem: Active): Boolean =
        oldItem == newItem
}