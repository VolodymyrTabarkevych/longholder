package com.traday.longholder.presentation.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.traday.longholder.R
import com.traday.longholder.databinding.ItemDropdownCurrencyBinding
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.extensions.loadWithGlide

class CurrencyAdapter(
    context: Context, currencies: List<Currency>
) : ArrayAdapter<Currency>(context, R.layout.item_dropdown_currency, currencies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)!!
        val holder: CurrencyItemViewHolder
        if (convertView == null) {
            holder = CurrencyItemViewHolder(
                ItemDropdownCurrencyBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            holder.binding.root.tag = holder
        } else {
            holder = (convertView.tag as CurrencyItemViewHolder)
        }
        with(holder.binding) {
            tvDropdownCurrency.text = item.name
            ivDropdownCurrency.loadWithGlide(item.linkToPhoto)
        }
        return holder.binding.root
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)!!.id.toLong()
    }
}