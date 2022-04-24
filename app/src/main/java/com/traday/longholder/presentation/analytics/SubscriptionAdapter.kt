package com.traday.longholder.presentation.analytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.databinding.ItemSubscriptionBinding

class SubscriptionAdapter : RecyclerView.Adapter<SubscriptionAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            ItemSubscriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {}

    override fun getItemCount(): Int = ITEM_COUNT

    class OnboardingItemViewHolder(val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {

        private const val ITEM_COUNT = 3
    }
}