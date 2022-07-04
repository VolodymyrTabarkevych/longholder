package com.traday.longholder.domain.model

import android.os.Parcelable
import com.traday.longholder.R
import com.traday.longholder.extensions.replaceDotWithComma
import com.traday.longholder.utils.EMPTY_STRING
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Active(
    val id: Int,
    val name: String?,
    val valueOfCrypto: Double,
    val cryptoPriceOnStart: Double,
    val cryptoPriceOnEnd: Double,
    val priceInOtherCurrencyOnStart: Double,
    val priceInOtherCurrencyOnEnd: Double,
    val currentCurrencyPrice: Double,
    val nameOfCurrency: String?,
    val dateOfStart: String,
    val dateOfEnd: String,
    val comment: String?,
    val linkToImage: String?,
    val earnedMoney: Double,
    val percents: Double,
    val symbol: String,
    val wantedPercents: Double
) : Parcelable {

    val earnedMoneyFormatted: String get() = (if (earnedMoney >= 0.0) "+" else EMPTY_STRING) + earnedMoney.replaceDotWithComma()

    val earnedMoneyResIdColor: Int get() = if (earnedMoney >= 0.0) R.color.limeade else R.color.thunderbird

    val nameFormatted: String? get() = name?.replaceFirstChar(Char::titlecase)

    val valueOfCryptoFormatted: String get() = valueOfCrypto.replaceDotWithComma()

    val priceInOtherCurrencyOnStartFormatted: String get() = priceInOtherCurrencyOnStart.replaceDotWithComma()

    val priceInOtherCurrencyOnEndFormatted: String get() = priceInOtherCurrencyOnEnd.replaceDotWithComma()

    val currentCurrencyPriceFormatted: String get() = currentCurrencyPrice.replaceDotWithComma()

    val currentCurrencyPriceSummaryFormatted: String
        get() = calculateCurrentCurrencyPriceSummary(
            valueOfCrypto = valueOfCrypto,
            currentCurrencyPrice = currentCurrencyPrice
        ).replaceDotWithComma()

    val percentsFormatted: String get() = percents.replaceDotWithComma()

    val symbolFormatted: String get() = symbol.uppercase(Locale.ENGLISH)

    private fun calculateCurrentCurrencyPriceSummary(
        valueOfCrypto: Double,
        currentCurrencyPrice: Double
    ): Double {
        return valueOfCrypto * currentCurrencyPrice
    }
}