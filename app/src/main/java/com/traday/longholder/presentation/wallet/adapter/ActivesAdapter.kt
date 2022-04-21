package com.traday.longholder.presentation.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.traday.longholder.databinding.ItemActiveBinding
import com.traday.longholder.domain.model.Active

class ActivesAdapter(
    private val activeEventListener: ActiveItemViewHolder.EventListener
) : ListAdapter<Active, ActiveItemViewHolder>(DiffCallback()) {

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

    private class DiffCallback : DiffUtil.ItemCallback<Active>() {

        override fun areItemsTheSame(oldItem: Active, newItem: Active): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Active, newItem: Active): Boolean =
            oldItem == newItem
    }
}