package com.traday.longholder.presentation.wallet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.R
import com.traday.longholder.databinding.ItemActiveBinding
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.loadWithGlide

class ActiveItemViewHolder(
    private val binding: ItemActiveBinding,
    private val eventListener: EventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Active) {
        with(binding) {
            root.setOnClickListener { eventListener.onActiveClicked(item) }
            ivActiveCryptoIcon.loadWithGlide(item.linkToImage)
            tvActiveCryptoName.text = item.nameFormatted
            tvActiveCryptoValue.text = item.valueOfCryptoFormatted
            tvActiveCryptoPriceInOtherCurrency.apply {
                text = resources.getString(
                    R.string.common_dollar_value_with_brackets,
                    item.priceInOtherCurrencyOnStartFormatted
                )
            }
            tvActiveCryptoEarnedMoney.apply {
                text = resources.getString(
                    R.string.common_earned_crypto_with_percent,
                    item.earnedMoneyFormatted,
                    item.percentsFormatted
                )
            }
            tvActiveCryptoCurrentPrice.apply {
                text = resources.getString(
                    R.string.common_crypto_price_in_other_currency,
                    item.nameFormatted,
                    item.currentCurrencyPriceFormatted
                )
            }
            tvActiveDate.apply {
                text = resources.getString(
                    R.string.wallet_on_hold_from,
                    item.dateOfStart
                )
            }
        }
    }

    interface EventListener {

        fun onActiveClicked(active: Active)
    }
}