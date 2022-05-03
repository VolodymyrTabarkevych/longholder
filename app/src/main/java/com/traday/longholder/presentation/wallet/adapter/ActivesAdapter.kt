package com.traday.longholder.presentation.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.traday.longholder.databinding.ItemActiveBinding
import com.traday.longholder.domain.model.Active
import com.traday.longholder.presentation.common.diff_callback.ActiveDiffCallback

class ActivesAdapter(
    private val activeEventListener: ActiveItemViewHolder.EventListener
) : ListAdapter<Active, ActiveItemViewHolder>(ActiveDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveItemViewHolder =
        ActiveItemViewHolder(
            ItemActiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), activeEventListener
        )

    override fun onBindViewHolder(holder: ActiveItemViewHolder, position: Int) =
        holder.bind(getItem(position))
}