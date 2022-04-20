package com.traday.longholder.presentation.notifications.adapter

import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.databinding.ItemNotificationBinding
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.extensions.getDrawableCompat

class NotificationItemViewHolder(private val binding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Notification) {
        with(binding) {
            ivNotification.apply { setImageDrawable(getDrawableCompat(item.icon)) }
            tvNotificationTitle.text = item.cryptoName
            tvNotificationDate.text = item.date
            tvNotificationStatus.text = item.status
            tvNotificationEarned.text = item.earned
        }
    }
}
