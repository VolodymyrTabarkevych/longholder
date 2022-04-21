package com.traday.longholder.presentation.wallet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.databinding.ItemActiveBinding
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.getDrawableCompat

class ActiveItemViewHolder(
    private val binding: ItemActiveBinding,
    private val eventListener: EventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Active) {
        with(binding) {
            root.setOnClickListener { eventListener.onActiveClicked(item) }
            ivActionCoinIcon.apply { setImageDrawable(getDrawableCompat(item.icon)) }
            tvActiveCoinName.text = item.name
            tvActiveCoinAmount.text = item.amount
            tvActiveCoinAmountInDollars.text = item.amountInDollars
            tvActiveCoinProfit.text = item.profit
            tvActiveCoinPrice.text = item.cryptoPrice
            tvActiveCoinHoldFrom.text = item.date
        }
    }

    interface EventListener {

        fun onActiveClicked(active: Active)
    }
}