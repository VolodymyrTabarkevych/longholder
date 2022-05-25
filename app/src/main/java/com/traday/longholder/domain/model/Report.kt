package com.traday.longholder.domain.model

import com.traday.longholder.R
import com.traday.longholder.extensions.replaceDotWithComma
import com.traday.longholder.utils.EMPTY_STRING

data class Report(
    val id: Int,
    val name: String?,
    val currencyCode: String?,
    val profit: Double,
    val allMoney: Double,
    val countOfCrypto: Double,
    val percents: Double,
    val priceNow: Double,
    val coinName: String?,
    val actives: List<Active>,
    val dateOfReport: String
) {

    val profitFormatted: String get() = (if (profit >= 0.0) "+" else EMPTY_STRING) + profit.replaceDotWithComma()

    val profitResIdColor: Int get() = if (profit >= 0.0) R.color.limeade else R.color.thunderbird

    val allMoneyFormatted: String get() = allMoney.replaceDotWithComma()

    val countOfCryptoFormatted: String get() = countOfCrypto.toString()

    val priceNowFormatted: String get() = priceNow.replaceDotWithComma()

    val getPercentsDrawableResId: Int get() = if (profit >= 0.0) R.drawable.ic_arrow_to_top else R.drawable.ic_arrow_to_bottom
}