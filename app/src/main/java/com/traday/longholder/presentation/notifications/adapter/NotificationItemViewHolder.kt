package com.traday.longholder.presentation.notifications.adapter

import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.databinding.ItemNotificationBinding
import com.traday.longholder.domain.model.Notification

class NotificationItemViewHolder(private val binding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Notification) {
        with(binding) {
            //ivNotification.apply { setImageDrawable(getDrawableCompat(item.icon)) }
            tvNotificationTitle.text = item.name
            tvNotificationDate.text = item.dateOfSent
            tvNotificationDescription.text = item.valueOfMessage
            tvNotificationEarned.text = item.dateOfStart
        }
    }
}
