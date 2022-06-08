package com.traday.longholder.domain.model

import com.traday.longholder.R
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty
import com.traday.longholder.extensions.replaceDotWithComma
import com.traday.longholder.utils.EMPTY_STRING

data class Notification(
    val id: Int, val activeId: Int,
    val name: String,
    val nameOfCoin: String,
    val valueOfMessage: String?,
    val earnedMoney: Double,
    val linkToTheImage: String?,
    val dateOfSent: String,
    val dateOfStart: String
) {

    val earnedMoneyFormatted: String get() = (if (earnedMoney >= 0.0) "+" else EMPTY_STRING) + earnedMoney.replaceDotWithComma()

    val earnedMoneyResIdColor: Int get() = if (earnedMoney >= 0.0) R.color.limeade else R.color.thunderbird

    val nameOfCoinFormatted: String get() = nameOfCoin.replaceFirstChar { it.uppercase() }

    val dateOfSentFormatted: String get() = dateOfSent.formatDateServerFormatToClientFormatOrEmpty()

    val dateOfStartFormatted: String get() = dateOfStart.formatDateServerFormatToClientFormatOrEmpty()
}